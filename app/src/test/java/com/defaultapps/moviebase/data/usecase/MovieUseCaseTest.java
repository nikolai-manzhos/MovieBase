package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.data.network.NetworkService;
import com.defaultapps.moviebase.utils.ResponseOrError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class MovieUseCaseTest {

    @Mock
    NetworkService networkService;

    @Mock
    Api api;

    @Mock
    LocalService localService;

    @Mock
    FirebaseService firebaseService;

    @Mock
    FavoritesManager favoritesManager;

    private MovieUseCase movieUseCase;
    private TestScheduler testScheduler;
    private final int MOVIE_ID = 9876021;
    private final String POSTER_PATH = "/aJn9XeesqsrSLKcHfHP4u5985hn.jpg";


    private MovieInfoResponse actualResponse;
    private Boolean actualFabState;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        movieUseCase = new MovieUseCaseImpl(networkService, localService,
                new TestSchedulerProvider(testScheduler), favoritesManager);
    }

    @Test
    public void requestMovieDataSuccess() throws Exception {
        MovieInfoResponse expectedResponse = new MovieInfoResponse();
        expectedResponse.setId(MOVIE_ID);
        Observable<MovieInfoResponse> observable = Observable.just(expectedResponse).subscribeOn(testScheduler);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getMovieInfo(anyInt(), anyString(), anyString(), anyString())).thenReturn(observable);
        when(favoritesManager.fetchAllFavs()).thenReturn(Observable.just(new ArrayList<>()));

        movieUseCase.requestMovieData(MOVIE_ID, false).subscribe(
                movieInfoResponse -> actualResponse = movieInfoResponse,
                err -> {}
        );
        testScheduler.triggerActions();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getCurrentStateSuccess() throws Exception {
        Observable<Boolean> observable = Observable.just(true).subscribeOn(testScheduler);
        when(favoritesManager.getIsFavoriteObservable(MOVIE_ID)).thenReturn(observable);

        movieUseCase.getCurrentState(MOVIE_ID).subscribe(
                isActive -> actualFabState = isActive,
                err -> {}
        );
        testScheduler.triggerActions();

        if (!actualFabState) {
            fail();
        }
    }

    @Test
    public void addOrRemoveFromFavoritesSuccess() throws Exception {
        Observable<ResponseOrError<Boolean>> observable = Observable.just(ResponseOrError.fromData(true)).subscribeOn(testScheduler);
        when(favoritesManager.addOrRemoveFromFavs(anyInt(), anyString())).thenReturn(observable);

        movieUseCase.addOrRemoveFromDatabase(MOVIE_ID, POSTER_PATH).subscribe(
                responseOrError -> assertEquals(responseOrError.isData(), true),
                err -> {}
        );
        testScheduler.triggerActions();
    }
}
