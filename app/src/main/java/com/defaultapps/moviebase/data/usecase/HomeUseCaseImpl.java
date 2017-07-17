package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;


@Singleton
public class HomeUseCaseImpl implements HomeUseCase {

    private NetworkService networkService;
    private SchedulerProvider schedulerProvider;

    private Disposable moviesDisposable;
    private ReplaySubject<List<MoviesResponse>> moviesReplaySubject;

    private final String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    HomeUseCaseImpl(NetworkService networkService,
                           SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<List<MoviesResponse>> requestHomeData(boolean force) {
        if (force && moviesDisposable != null) {
            moviesDisposable.dispose();
        }
        if (moviesDisposable == null || moviesDisposable.isDisposed()) {
            moviesReplaySubject = ReplaySubject.create();

            moviesDisposable = network()
                    .filter(moviesResponses -> moviesResponses.size() != 0)
                    .subscribe(moviesReplaySubject::onNext, moviesReplaySubject::onError);
        }
        return moviesReplaySubject;
    }

    private Single<List<MoviesResponse>> network() {
        return Single.zip(
                networkUpcoming(),
                networkNowPLaying(),
                (upcomingResponse, nowPlayingResponse) -> Arrays.asList(upcomingResponse, nowPlayingResponse));
    }

    private Single<MoviesResponse> networkUpcoming() {
        return networkService.getNetworkCall().getUpcomingMovies(API_KEY, "en-Us", 1)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Single<MoviesResponse> networkNowPLaying() {
        return networkService.getNetworkCall().getNowPlaying(API_KEY, "en-Us", 1)
                .compose(schedulerProvider.applyIoSchedulers());
    }
}
