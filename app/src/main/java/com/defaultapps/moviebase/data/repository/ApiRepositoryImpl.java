package com.defaultapps.moviebase.data.repository;


import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.person.PersonResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

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

    private static final String PERSON_APPEND_TO_RESPONSE = "movie_credits";
    private static final String MOVIE_DETAIL_APPEND_TO_RESPONSE = "videos,credits,similar";

    private final Api api;
    private final FirebaseService firebaseService;
    private final AppPreferencesManager preferencesManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    ApiRepositoryImpl(Api api,
                      FirebaseService firebaseService,
                      AppPreferencesManager preferencesManager,
                      SchedulerProvider schedulerProvider) {
        this.api = api;
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
        return api.getSearchQuery(MDB_API_KEY, LANGUAGE, query, page, preferencesManager.getAdultStatus())
                .flatMap(this::filterBannedMovies)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    @Override
    public Single<MoviesResponse> requestGenreMovies(String genreId, int page) {
        return api.discoverMovies(MDB_API_KEY, LANGUAGE, preferencesManager.getAdultStatus(), page, genreId)
                .flatMap(this::filterBannedMovies)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    @Override
    public Single<MovieDetailResponse> requestMovieDetails(int movieId) {
        return api.getMovieDetails(movieId, MDB_API_KEY, LANGUAGE, MOVIE_DETAIL_APPEND_TO_RESPONSE)
                .flatMap(movieDetailResponse -> filterBannedMovies(movieDetailResponse.getSimilar())
                        .map(moviesResponse -> {
                            movieDetailResponse.setSimilar(moviesResponse);
                            return movieDetailResponse;
                        }))
                .compose(schedulerProvider.applyIoSchedulers());
    }

    @Override
    public Single<PersonResponse> requestPersonDetails(int personId) {
        return api.getPersonDetails(personId, MDB_API_KEY, LANGUAGE, PERSON_APPEND_TO_RESPONSE)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Single<MoviesResponse> networkUpcoming() {
        return api.getUpcomingMovies(MDB_API_KEY, LANGUAGE, START_PAGE)
                .flatMap(this::filterBannedMovies);
    }

    private Single<MoviesResponse> networkNowPLaying() {
        return api.getNowPlaying(MDB_API_KEY, LANGUAGE, START_PAGE)
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
