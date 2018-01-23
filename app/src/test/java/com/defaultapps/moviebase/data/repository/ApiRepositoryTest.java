package com.defaultapps.moviebase.data.repository;


import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.person.PersonResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ApiRepositoryTest {

    @Mock
    private Api api;

    @Mock
    private FirebaseService firebaseService;

    @Mock
    private AppPreferencesManager appPreferencesManager;

    private ApiRepository apiRepository;

    private static final int ANY_PERSON_ID = 23131;
    private static final int ANY_MOVIE_ID = 232414;
    private static final String ANY_QUERY = "movie_query";
    private static final String ANY_GENRE_ID = "w23ks11";
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        apiRepository = new ApiRepositoryImpl(api, firebaseService,
                appPreferencesManager, new TestSchedulerProvider(Schedulers.trampoline()));
    }

    @Test
    public void requestHomeDataSuccess() {
        MoviesResponse upcomingResponse = random(MoviesResponse.class);
        MoviesResponse nowPlayingResponse = random(MoviesResponse.class);

        when(api.getUpcomingMovies(anyString(), anyString(), anyInt()))
                .thenReturn(Single.just(upcomingResponse));
        when(api.getNowPlaying(anyString(), anyString(), anyInt()))
                .thenReturn(Single.just(nowPlayingResponse));

        apiRepository.requestHomeData()
                .test()
                .assertNoErrors()
                .assertValue(response -> response.get(0).equals(upcomingResponse) && response.get(1).equals(nowPlayingResponse));
    }

    @Test
    public void requestHomeDataSingleSourceFailure() {
        MoviesResponse upcomingResponse = random(MoviesResponse.class);
        Exception nowPlayingException = new Exception();

        when(api.getUpcomingMovies(anyString(), anyString(), anyInt()))
                .thenReturn(Single.just(upcomingResponse));
        when(api.getNowPlaying(anyString(), anyString(), anyInt()))
                .thenReturn(Single.error(nowPlayingException));

        apiRepository.requestHomeData()
                .test()
                .assertError(nowPlayingException);
    }

    @Test
    public void requestPersonDetailsSuccess() {
        PersonResponse response = random(PersonResponse.class);

        when(api.getPersonDetails(eq(ANY_PERSON_ID), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(response));

        apiRepository.requestPersonDetails(ANY_PERSON_ID)
                .test()
                .assertNoErrors()
                .assertValue(response);
    }

    @Test
    public void requestMovieDetailsSuccess() {
        MovieDetailResponse response = random(MovieDetailResponse.class);

        when(api.getMovieDetails(eq(ANY_MOVIE_ID), anyString(), anyString(), anyString()))
                .thenReturn(Single.just(response));

        apiRepository.requestMovieDetails(ANY_MOVIE_ID)
                .test()
                .assertNoErrors()
                .assertValue(response);
    }

    @Test
    public void requestSearchResultsSuccess() {
        MoviesResponse response = random(MoviesResponse.class);

        when(api.getSearchQuery(anyString(), anyString(), eq(ANY_QUERY), eq(1), anyBoolean()))
                .thenReturn(Single.just(response));

        apiRepository.requestSearchResults(ANY_QUERY, 1)
                .test()
                .assertNoErrors()
                .assertValue(response);
    }

    @Test
    public void requestGenreMoviesSuccess() {
        MoviesResponse response = random(MoviesResponse.class);

        when(api.discoverMovies(anyString(), anyString(), anyBoolean(), anyInt(), eq(ANY_GENRE_ID)))
                .thenReturn(Single.just(response));

        apiRepository.requestGenreMovies(ANY_GENRE_ID, 1)
                .test()
                .assertNoErrors();
    }

}
