package com.defaultapps.moviebase.data.repository;


import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

import static com.defaultapps.moviebase.BuildConfig.MDB_API_KEY;

@Singleton
public class ApiRepositoryImpl implements ApiRepository {

    private static final String LANGUAGE = "en-Us";
    private static final int START_PAGE = 1;

    private final NetworkService networkService;
    private final FirebaseService firebaseService;
    private final AppPreferencesManager preferencesManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    ApiRepositoryImpl(NetworkService networkService,
                             FirebaseService firebaseService,
                             AppPreferencesManager preferencesManager,
                             SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.firebaseService = firebaseService;
        this.preferencesManager = preferencesManager;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<List<MoviesResponse>> requestHomeData() {
        return Single.zip(
                networkUpcoming(),
                networkNowPLaying(),
                (upcomingResponse, nowPlayingResponse) -> Arrays.asList(upcomingResponse, nowPlayingResponse))
                .compose(schedulerProvider.applyIoSchedulers());
    }

    @Override
    public Single<MoviesResponse> requestSearchResults(String query, int page) {
        return networkService.getNetworkCall().getSearchQuery(MDB_API_KEY, LANGUAGE, query, page,
                preferencesManager.getAdultStatus()).flatMap(this::filterBannedMovies)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Single<MoviesResponse> networkUpcoming() {
        return networkService.getNetworkCall().getUpcomingMovies(MDB_API_KEY, LANGUAGE, START_PAGE)
                .flatMap(this::filterBannedMovies);
    }

    private Single<MoviesResponse> networkNowPLaying() {
        return networkService.getNetworkCall().getNowPlaying(MDB_API_KEY, LANGUAGE, START_PAGE)
                .flatMap(this::filterBannedMovies);
    }

    private Single<MoviesResponse> filterBannedMovies(MoviesResponse moviesResponse) {
        List<String> bannedIds = firebaseService.getBlockedMoviesId();
        return Observable.fromIterable(moviesResponse.getResults())
                .filter(result -> !bannedIds.contains(result.getId().toString()))
                .toList()
                .map(results -> {
                    moviesResponse.setResults(results);
                    return moviesResponse;
                });
    }
}
