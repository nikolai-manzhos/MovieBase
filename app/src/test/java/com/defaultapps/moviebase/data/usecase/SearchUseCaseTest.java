package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.data.network.NetworkService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class SearchUseCaseTest {

    @Mock
    NetworkService networkService;

    @Mock
    Api api;

    @Mock
    AppPreferencesManager preferencesManager;

    private SearchUseCase searchUseCase;
    private TestScheduler testScheduler;

    private MoviesResponse actualResponse;
    private Throwable actualException;
    private final String QUERY = "Titanic";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        searchUseCase = new SearchUseCaseImpl(networkService, new TestSchedulerProvider(testScheduler), preferencesManager);
        when(preferencesManager.getAdultStatus()).thenReturn(false);
    }

    @Test
    public void requestSearchResultsSuccess() throws Exception {
        MoviesResponse expectedResponse = new MoviesResponse();
        Observable<MoviesResponse> observable = Observable.just(expectedResponse).subscribeOn(testScheduler);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getSearchQuery(anyString(), anyString(), anyString(), anyInt(), anyBoolean())).thenReturn(observable);

        searchUseCase.requestSearchResults(QUERY, false).subscribe(
                actualResponse -> this.actualResponse = actualResponse,
                err -> {}
        );

        testScheduler.triggerActions();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void requestSearchResultFailure() throws Exception {
        Throwable expectedException = new Throwable("Network error.");
        Observable<MoviesResponse> observable = Observable.error(expectedException);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getSearchQuery(anyString(), anyString(), anyString(), anyInt(), anyBoolean())).thenReturn(observable);

        searchUseCase.requestSearchResults(QUERY, false).subscribe(
                actualResponse -> {},
                err -> actualException = err
        );
        testScheduler.triggerActions();

        assertNotNull(actualException);
        assertEquals(expectedException, actualException);

    }
}
