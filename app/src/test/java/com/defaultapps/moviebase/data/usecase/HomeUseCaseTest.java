package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.data.network.NetworkService;
import com.defaultapps.moviebase.utils.rx.RxBus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


public class HomeUseCaseTest {

    @Mock
    LocalService localService;

    @Mock
    NetworkService networkService;

    @Mock
    Api api;

    @Mock
    RxBus rxBus;

    private HomeUseCase discoverUseCase;
    private TestScheduler testScheduler;

    private MoviesResponse actualResponse1;
    private MoviesResponse actualResponse2;
    private Throwable expectedThrowable;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        discoverUseCase = new HomeUseCaseImpl(networkService,
                rxBus,
                new TestSchedulerProvider(testScheduler));
    }

    @Test
    public void fetchHomePageDataSuccess() throws Exception {
        MoviesResponse expectedResponse1 = new MoviesResponse();
        MoviesResponse expectedResponse2 = new MoviesResponse();

        Single<MoviesResponse> single1 = Single.just(expectedResponse1);
        Single<MoviesResponse> single2 = Single.just(expectedResponse2);

        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getUpcomingMovies(anyString(), anyString(), anyInt())).thenReturn(single1);
        when(api.getNowPlaying(anyString(), anyString(), anyInt())).thenReturn(single2);

        discoverUseCase.requestHomeData(true).subscribe(moviesResponses -> {
            actualResponse1 = moviesResponses.get(0);
            actualResponse2 = moviesResponses.get(1);
        });

        testScheduler.triggerActions();

        assertNotNull(actualResponse1);
        assertNotNull(actualResponse2);
        assertEquals(expectedResponse1, actualResponse1);
        assertEquals(expectedResponse2, actualResponse2);
    }

    @Test
    public void fetchHomePageDataFailure() throws Exception {
        Exception exception = new Exception("Network error.");
        Single<MoviesResponse> single = Single.error(exception);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getUpcomingMovies(anyString(), anyString(), anyInt())).thenReturn(single);
        when(api.getNowPlaying(anyString(), anyString(), anyInt())).thenReturn(single);

        discoverUseCase.requestHomeData(true).subscribe(moviesResponses -> {}, err -> expectedThrowable = err);

        testScheduler.triggerActions();
        assertNotNull(expectedThrowable);
        assertEquals(exception, expectedThrowable);
    }
}
