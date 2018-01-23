package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.domain.base.BaseUseCase;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.domain.repository.ApiRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class SearchUseCaseImpl extends BaseUseCase implements SearchUseCase {

    private static final int INITAL_PAGE = 1;

    private final ApiRepository apiRepository;

    private Disposable disposable;
    private Disposable paginationDisposable;
    private BehaviorSubject<MoviesResponse> behaviorSubject;

    @Inject
    SearchUseCaseImpl(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public Observable<MoviesResponse> requestSearchResults(String query, boolean force) {
        if (force && disposable != null) disposable.dispose();
        if (disposable == null || disposable.isDisposed()) {
            behaviorSubject = BehaviorSubject.create();

            disposable = apiRepository.requestSearchResults(query, INITAL_PAGE)
                    .doOnSubscribe(it -> getCompositeDisposable().add(it))
                    .subscribe(behaviorSubject::onNext, behaviorSubject::onError);
        }
        return behaviorSubject;
    }

    @Override
    public Observable<MoviesResponse> requestMoreSearchResults(String query) {
        if (paginationDisposable != null && !paginationDisposable.isDisposed()) {
            paginationDisposable.dispose();
        }
        MoviesResponse previousResult = behaviorSubject.getValue();
        PublishSubject<MoviesResponse> paginationResult = PublishSubject.create();
        paginationDisposable = apiRepository.requestSearchResults(query, previousResult.getPage() + 1)
                .doOnSubscribe(it -> getCompositeDisposable().add(it))
                .map(moviesResponse -> {
                    previousResult.getResults().addAll(moviesResponse.getResults());
                    previousResult.setPage(moviesResponse.getPage());
                    return previousResult;
                })
                .subscribe(
                        response -> {
                            paginationResult.onNext(response);
                            behaviorSubject = BehaviorSubject.create();
                            behaviorSubject.onNext(response);
                        },
                        paginationResult::onError
                );
        return paginationResult;
    }
}
