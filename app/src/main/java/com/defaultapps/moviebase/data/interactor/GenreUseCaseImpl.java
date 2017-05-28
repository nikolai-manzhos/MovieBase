package com.defaultapps.moviebase.data.interactor;


import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;


@Singleton
public class GenreUseCaseImpl implements GenreUseCase {

    private NetworkService networkService;
    private SchedulerProvider schedulerProvider;

    private Disposable genreDisposable;
    private ReplaySubject<MoviesResponse> genreReplayProcessor;
    private MoviesResponse memoryCache;

    private String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    GenreUseCaseImpl(NetworkService networkService,
                            SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<MoviesResponse> requestHomeData(String genreId, boolean force) {
        if (force && genreDisposable != null) {
            memoryCache = null;
            genreDisposable.dispose();
        }
        if (genreDisposable == null || genreDisposable.isDisposed()) {
            genreReplayProcessor = ReplaySubject.create();

            genreDisposable = Observable.concat(memory(), network(genreId))
                    .filter(moviesResponse -> moviesResponse.getTotalResults() != null).firstOrError()
                    .subscribe(genreReplayProcessor::onNext, genreReplayProcessor::onError);
        }
        return genreReplayProcessor;
    }

    private Observable<MoviesResponse> network(String genreId) {
        return networkService.getNetworkCall().discoverMovies(API_KEY, "en-US", false, 1, genreId)
                .doOnNext(moviesResponse -> memoryCache = moviesResponse)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Observable<MoviesResponse> memory() {
        if (memoryCache != null) {
            return Observable.just(memoryCache);
        }
        return Observable.just(new MoviesResponse());
    }
}
