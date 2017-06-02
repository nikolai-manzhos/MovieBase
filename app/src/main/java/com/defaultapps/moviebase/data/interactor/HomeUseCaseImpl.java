package com.defaultapps.moviebase.data.interactor;

import android.util.Log;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;


@Singleton
public class HomeUseCaseImpl implements HomeUseCase {

    private NetworkService networkService;
    private LocalService localService;
    private SchedulerProvider schedulerProvider;

    private Disposable moviesDisposable;
    private ReplaySubject<List<MoviesResponse>> moviesReplaySubject;

    private final String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    HomeUseCaseImpl(NetworkService networkService,
                           LocalService localService,
                           SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.localService = localService;
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
                    .filter(moviesResponses -> moviesResponses.size() != 0).firstOrError()
                    .subscribe(moviesReplaySubject::onNext, moviesReplaySubject::onError);
        }
        return moviesReplaySubject;
    }

    private Observable<List<MoviesResponse>> network() {
        return Observable.zip(
                networkUpcoming(),
                networkNowPLaying(),
                (upcomingResponse, nowPlayingResponse) -> Arrays.asList(upcomingResponse, nowPlayingResponse));
    }

    private Observable<MoviesResponse> networkUpcoming() {
        return networkService.getNetworkCall().getUpcomingMovies(API_KEY, "en-Us", 1)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Observable<MoviesResponse> networkNowPLaying() {
        return networkService.getNetworkCall().getNowPlaying(API_KEY, "en-Us", 1)
                .compose(schedulerProvider.applyIoSchedulers());
    }
}
