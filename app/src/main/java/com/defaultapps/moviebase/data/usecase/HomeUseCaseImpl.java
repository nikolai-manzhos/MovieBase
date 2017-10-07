package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.rx.RxBus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;


@Singleton
public class HomeUseCaseImpl implements HomeUseCase {

    private NetworkService networkService;
    private RxBus rxBus;
    private SchedulerProvider schedulerProvider;

    private Disposable moviesDisposable;
    private BehaviorSubject<List<MoviesResponse>> moviesBehaviorSubject;
    private List<MoviesResponse> cache;

    private final String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    HomeUseCaseImpl(NetworkService networkService,
                           RxBus rxBus,
                           SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.rxBus = rxBus;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<List<MoviesResponse>> requestHomeData(boolean force) {
        if (cache != null
                && moviesBehaviorSubject != null
                && !moviesBehaviorSubject.hasValue()
                && !moviesBehaviorSubject.hasThrowable()) {
            rxBus.publish(AppConstants.HOME_INSTANT_CACHE, cache);
        }
        if (force && moviesDisposable != null) {
            moviesDisposable.dispose();
        } else if (!force
                && cache != null
                && moviesBehaviorSubject != null
                && moviesBehaviorSubject.hasThrowable()) {
            rxBus.publish(AppConstants.HOME_INSTANT_CACHE, cache);
            return Observable.empty();
        }
        if (moviesDisposable == null || moviesDisposable.isDisposed()) {
            moviesBehaviorSubject = BehaviorSubject.create();

            moviesDisposable = network()
                    .doOnSuccess(moviesList -> cache = moviesList)
                    .subscribe(moviesBehaviorSubject::onNext, moviesBehaviorSubject::onError);
        }
        return moviesBehaviorSubject;
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
