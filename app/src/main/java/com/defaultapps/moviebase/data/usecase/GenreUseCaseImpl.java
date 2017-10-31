package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


@Singleton
public class GenreUseCaseImpl implements GenreUseCase {

    private final NetworkService networkService;
    private final AppPreferencesManager preferencesManager;
    private final SchedulerProvider schedulerProvider;

    private Disposable genreDisposable;
    private Disposable genrePaginationDisposable;
    private BehaviorSubject<MoviesResponse> genreBehaviorSubject;

    @Inject
    GenreUseCaseImpl(NetworkService networkService,
                     SchedulerProvider schedulerProvider,
                     AppPreferencesManager preferencesManager) {
        this.networkService = networkService;
        this.schedulerProvider = schedulerProvider;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public Observable<MoviesResponse> requestGenreData(String genreId, boolean force) {
        if (force && genreDisposable != null) {
            genreDisposable.dispose();
        }
        if (genreDisposable == null || genreDisposable.isDisposed()) {
            genreBehaviorSubject = BehaviorSubject.create();

            genreDisposable = network(genreId, 1)
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
        genrePaginationDisposable = network(genreId, genreBehaviorSubject.getValue().getPage() + 1)
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

    private Single<MoviesResponse> network(String genreId, int page) {
        String API_KEY = BuildConfig.MDB_API_KEY;
        return networkService.getNetworkCall().discoverMovies(API_KEY, "en-US", preferencesManager.getAdultStatus(), page, genreId)
                .compose(schedulerProvider.applyIoSchedulers());
    }
}
