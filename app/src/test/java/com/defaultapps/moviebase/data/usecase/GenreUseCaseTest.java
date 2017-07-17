package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.data.network.NetworkService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class GenreUseCaseTest {

    @Mock
    NetworkService networkService;

    @Mock
    LocalService localService;

    @Mock
    Api api;

    @Mock
    AppPreferencesManager preferencesManager;

    private GenreUseCase genreUseCase;
    private TestScheduler testScheduler;

    private MoviesResponse actualResponse;
    private Throwable actualException;
    private final String GENRE_ID = "2979254";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        genreUseCase = new GenreUseCaseImpl(networkService, new TestSchedulerProvider(testScheduler), preferencesManager);
        when(preferencesManager.getAdultStatus()).thenReturn(false);
    }

    @Test
    public void requestGenreDataSuccess() throws Exception {
        MoviesResponse expectedResponse = new MoviesResponse();
        Single<MoviesResponse> single = Single.just(expectedResponse);

        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.discoverMovies(anyString(), anyString(), anyBoolean(), anyInt(), anyString()))
                .thenReturn(single);

        genreUseCase.requestGenreData(GENRE_ID, true).subscribe(moviesResponse -> actualResponse = moviesResponse);

        testScheduler.triggerActions();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void requestGenreDataFailure() throws Exception {
        Exception expectedException = new Exception("Network exception.");
        Single<MoviesResponse> single = Single.error(expectedException);

        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.discoverMovies(anyString(), anyString(), anyBoolean(), anyInt(), anyString()))
                .thenReturn(single);

        genreUseCase.requestGenreData(GENRE_ID, true).subscribe(
                moviesResponse -> {},
                err -> actualException = err
        );

        testScheduler.triggerActions();

        assertNotNull(actualException);
        assertEquals(expectedException, actualException);
    }
}
