package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class MovieUseCaseImpl implements MovieUseCase {

    private NetworkService networkService;
    private LocalService localService;
    private FirebaseService firebaseService;
    private SchedulerProvider schedulerProvider;

    private Disposable movieInfoDisposable;
    private ReplaySubject<MovieInfoResponse> movieInfoReplaySubject;
    private int currentId = -1;

    private final String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    MovieUseCaseImpl(NetworkService networkService,
                            LocalService localService,
                            SchedulerProvider schedulerProvider,
                            FirebaseService firebaseService) {
        this.networkService = networkService;
        this.localService = localService;
        this.schedulerProvider = schedulerProvider;
        this.firebaseService = firebaseService;
    }

    @Override
    public Observable<MovieInfoResponse> requestMovieData(int movieId, boolean force) {
        if (force) movieInfoDisposable.dispose();
        if (currentId != -1 && movieId != currentId && movieInfoDisposable != null) {
            currentId = -1;
            movieInfoDisposable.dispose();
        }
        if (movieInfoDisposable == null || movieInfoDisposable.isDisposed()) {
            movieInfoReplaySubject = ReplaySubject.create();
            currentId = movieId;

            movieInfoDisposable = network(movieId)
                    .filter(movieInfoResponse -> movieInfoResponse.getId() != null).firstOrError()
                    .subscribe(movieInfoReplaySubject::onNext, movieInfoReplaySubject::onError);
        }
        return movieInfoReplaySubject;
    }

    @Override
    public Observable<Boolean> addMovieToDatabase(int movieId, String posterPath) {
        return firebaseService.addToFavorites(movieId, posterPath);
    }

    private Observable<MovieInfoResponse> network(int movieId) {
        return networkService.getNetworkCall().getMovieInfo(movieId, API_KEY, "en-Us", "videos,credits,similar")
                .compose(schedulerProvider.applyIoSchedulers());
    }
}
