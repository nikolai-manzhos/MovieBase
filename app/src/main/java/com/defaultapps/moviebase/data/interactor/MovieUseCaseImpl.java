package com.defaultapps.moviebase.data.interactor;


import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class MovieUseCaseImpl implements MovieUseCase {

    private NetworkService networkService;
    private LocalService localService;
    private SchedulerProvider schedulerProvider;

    private Disposable movieInfoDisposable;
    private ReplaySubject<MovieInfoResponse> movieInfoReplaySubject;
    private MovieInfoResponse memoryCache;

    private final String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    MovieUseCaseImpl(NetworkService networkService,
                            LocalService localService,
                            SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.localService = localService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<MovieInfoResponse> requestMovieData(int movieId) {
        if (movieInfoDisposable == null || movieInfoDisposable.isDisposed()) {
            movieInfoReplaySubject = ReplaySubject.create();

            movieInfoDisposable = Observable.concat(memory(movieId), network(movieId))
                    .filter(movieInfoResponse -> movieInfoResponse.getId() != null).firstOrError()
                    .subscribe(movieInfoReplaySubject::onNext, movieInfoReplaySubject::onError);
        }
        return movieInfoReplaySubject;
    }

    private Observable<MovieInfoResponse> network(int movieId) {
        return networkService.getNetworkCall().getMovieInfo(movieId, API_KEY, "en-Us", "videos")
                .doOnNext(movieInfoResponse -> memoryCache = movieInfoResponse)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Observable<MovieInfoResponse> memory(int movieId) {
        if (memoryCache != null && memoryCache.getId() == movieId) {
            return Observable.just(memoryCache);
        }
        return Observable.just(new MovieInfoResponse());
    }
}
