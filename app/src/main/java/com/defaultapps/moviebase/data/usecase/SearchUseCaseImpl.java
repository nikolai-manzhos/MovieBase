package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.base.BaseUseCase;
import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class SearchUseCaseImpl extends BaseUseCase implements SearchUseCase {

    private final NetworkService networkService;
    private final AppPreferencesManager preferencesManager;
    private final SchedulerProvider schedulerProvider;
    private final FirebaseService firebaseService;

    private Disposable disposable;
    private Disposable paginationDisposable;
    private BehaviorSubject<MoviesResponse> behaviorSubject;

    @Inject
    SearchUseCaseImpl(NetworkService networkService,
                      SchedulerProvider schedulerProvider,
                      AppPreferencesManager preferencesManager,
                      FirebaseService firebaseService) {
        this.networkService = networkService;
        this.schedulerProvider = schedulerProvider;
        this.preferencesManager = preferencesManager;
        this.firebaseService = firebaseService;
    }

    @Override
    public Observable<MoviesResponse> requestSearchResults(String query, boolean force) {
        if (force && disposable != null) disposable.dispose();
        if (disposable == null || disposable.isDisposed()) {
            behaviorSubject = BehaviorSubject.create();

            disposable = network(query, 1)
                    .doOnSubscribe(it -> getCompositeDisposable().add(it))
                    .compose(schedulerProvider.applyIoSchedulers())
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
        paginationDisposable = network(query, previousResult.getPage() + 1)
                .doOnSubscribe(it -> getCompositeDisposable().add(it))
                .map(moviesResponse -> {
                    previousResult.getResults().addAll(moviesResponse.getResults());
                    previousResult.setPage(moviesResponse.getPage());
                    return previousResult;
                })
                .compose(schedulerProvider.applyIoSchedulers())
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

    private Single<MoviesResponse> network(String query, int page) {
        final String API_KEY = BuildConfig.MDB_API_KEY;
        return networkService.getNetworkCall()
                .getSearchQuery(API_KEY, "en-Us", query, page, preferencesManager.getAdultStatus())
                .flatMap(moviesResponse -> {
                    List<String> bannedIds = firebaseService.getBlockedMoviesId();
                    return Observable.fromIterable(moviesResponse.getResults())
                            .filter(result -> !bannedIds.contains(result.getId().toString()))
                            .toList()
                            .map(results -> {
                                moviesResponse.setResults(results);
                                return moviesResponse;
                            });
                });
    }
}
