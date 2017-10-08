package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.Api;
import com.defaultapps.moviebase.data.network.NetworkService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
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

    private MoviesResponse actualResponse;
    private Throwable actualException;
    private final String QUERY = "Titanic";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        searchUseCase = new SearchUseCaseImpl(networkService, new TestSchedulerProvider(Schedulers.trampoline()), preferencesManager);
        when(preferencesManager.getAdultStatus()).thenReturn(false);
    }

    @Test
    public void requestSearchResultsSuccess() throws Exception {
        MoviesResponse expectedResponse = new MoviesResponse();
        Single<MoviesResponse> single = Single.just(expectedResponse);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getSearchQuery(anyString(), anyString(), anyString(), anyInt(), anyBoolean())).thenReturn(single);

        searchUseCase.requestSearchResults(QUERY, false).subscribe(
                actualResponse -> this.actualResponse = actualResponse,
                err -> {}
        );

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void requestSearchResultFailure() throws Exception {
        Throwable expectedException = new Throwable("Network error.");
        Single<MoviesResponse> single = Single.error(expectedException);
        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getSearchQuery(anyString(), anyString(), anyString(), anyInt(), anyBoolean())).thenReturn(single);

        searchUseCase.requestSearchResults(QUERY, false).subscribe(
                actualResponse -> {},
                err -> actualException = err
        );
        assertNotNull(actualException);
        assertEquals(expectedException, actualException);
    }

    @Test
    public void requestMoreSearchResultsSuccess() throws Exception {
        MoviesResponse response = random(MoviesResponse.class);
        changeReplaySubject(response);

        MoviesResponse loadMoreResponse = random(MoviesResponse.class);
        Single<MoviesResponse> single = Single.just(loadMoreResponse);

        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getSearchQuery(anyString(), anyString(), anyString(), anyInt(), anyBoolean()))
                .thenReturn(single);

        searchUseCase.requestMoreSearchResults(QUERY)
                .subscribe(moviesResponse -> assertEquals(loadMoreResponse.getPage(), moviesResponse.getPage()));

        //If previous call still ongoing it should be disposed and new one fired.
        searchUseCase.requestMoreSearchResults(QUERY)
                .subscribe();
    }

    @Test
    public void requestMoreSearchResultsFailure() throws Exception {
        changeReplaySubject(random(MoviesResponse.class));

        Throwable throwable = new Throwable("Exception");
        Single<MoviesResponse> single = Single.error(throwable);

        when(networkService.getNetworkCall()).thenReturn(api);
        when(api.getSearchQuery(anyString(), anyString(), anyString(), anyInt(), anyBoolean()))
                .thenReturn(single);

        searchUseCase.requestMoreSearchResults(QUERY)
                .subscribe(response -> {}, Assert::assertNotNull);
    }

    private void changeReplaySubject(MoviesResponse response) throws Exception {
        Field field = SearchUseCaseImpl.class.getDeclaredField("replaySubject");
        field.setAccessible(true);
        field.set(searchUseCase, ReplaySubject.create(1));
        //noinspection unchecked
        ((ReplaySubject<MoviesResponse> ) field.get(searchUseCase)).onNext(response);
    }
}
