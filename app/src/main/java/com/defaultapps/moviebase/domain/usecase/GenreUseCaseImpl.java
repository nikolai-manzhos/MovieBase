package com.defaultapps.moviebase.domain.usecase;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.domain.base.BaseUseCase;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


@Singleton
public class GenreUseCaseImpl extends BaseUseCase implements GenreUseCase {

    private final ApiRepository apiRepository;

    private Disposable genreDisposable;
    private Disposable genrePaginationDisposable;
    private BehaviorSubject<MoviesResponse> genreBehaviorSubject;

    @Inject
    GenreUseCaseImpl(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public Observable<MoviesResponse> requestGenreData(String genreId, boolean force) {
        if (force && genreDisposable != null) {
            genreDisposable.dispose();
        }
        if (genreDisposable == null || genreDisposable.isDisposed()) {
            genreBehaviorSubject = BehaviorSubject.create();

            genreDisposable = apiRepository.requestGenreMovies(genreId, 1)
                    .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                    .subscribe(genreBehaviorSubject::onNext, genreBehaviorSubject::onError);
        }
        return genreBehaviorSubject;
    }

    @Override
    public Observable<MoviesResponse> requestMoreGenreData(String genreId) {
        if (genrePaginationDisposable != null && !genrePaginationDisposable.isDisposed()) {
            genrePaginationDisposable.dispose();
        }
        MoviesResponse previousResults = genreBehaviorSubject.getValue();
        PublishSubject<MoviesResponse> publishSubject = PublishSubject.create();
        genrePaginationDisposable = apiRepository.requestGenreMovies(genreId, genreBehaviorSubject.getValue().getPage() + 1)
                .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                .map(moviesResponse -> {
                    previousResults.getResults().addAll(moviesResponse.getResults());
                    previousResults.setPage(moviesResponse.getPage());
                    return previousResults;
                })
                .subscribe(moviesResponse -> {
                    publishSubject.onNext(moviesResponse);
                    genreBehaviorSubject = BehaviorSubject.create();
                    genreBehaviorSubject.onNext(moviesResponse);
                }, publishSubject::onError);
        return publishSubject;
    }
}
