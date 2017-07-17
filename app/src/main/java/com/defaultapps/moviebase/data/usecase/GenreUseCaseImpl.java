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
import io.reactivex.subjects.ReplaySubject;


@Singleton
public class GenreUseCaseImpl implements GenreUseCase {

    private NetworkService networkService;
    private AppPreferencesManager preferencesManager;
    private SchedulerProvider schedulerProvider;

    private Disposable genreDisposable;
    private ReplaySubject<MoviesResponse> genreReplayProcessor;

    private String API_KEY = BuildConfig.MDB_API_KEY;

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
            genreReplayProcessor = ReplaySubject.create();

            genreDisposable = network(genreId)
                    .subscribe(genreReplayProcessor::onNext, genreReplayProcessor::onError);
        }
        return genreReplayProcessor;
    }

    private Single<MoviesResponse> network(String genreId) {
        return networkService.getNetworkCall().discoverMovies(API_KEY, "en-US", preferencesManager.getAdultStatus(), 1, genreId)
                .compose(schedulerProvider.applyIoSchedulers());
    }
}
