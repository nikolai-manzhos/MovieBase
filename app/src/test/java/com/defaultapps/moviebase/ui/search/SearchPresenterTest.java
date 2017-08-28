package com.defaultapps.moviebase.ui.search;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.usecase.SearchUseCaseImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchPresenterTest {

    @Mock
    SearchContract.SearchView view;

    @Mock
    SearchUseCaseImpl useCase;

    @Mock
    MoviesResponse response;

    private SearchPresenterImpl presenter;
    private TestScheduler testScheduler;
    private final String QUERY = "Titanic";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new SearchPresenterImpl(useCase);
        testScheduler = new TestScheduler();
        presenter.onAttach(view);
    }

    @Test
    public void requestSearchResultsSuccess() throws Exception {
        Observable<MoviesResponse> observable = Observable.just(response).subscribeOn(testScheduler);
        when(useCase.requestSearchResults(anyString(), anyBoolean())).thenReturn(observable);

        presenter.requestSearchResults(QUERY, true);

        verify(view).hideData();
        verify(view).hideEmpty();
        verify(view).showLoading();

        when(response.getTotalResults()).thenReturn(0);
        testScheduler.triggerActions();

        verify(view, times(2)).hideError();
        verify(view).hideLoading();
        verify(view).showData();
        verify(view).displaySearchResults(response);
        verify(view, never()).showError();
    }

    @Test
    public void requestSearchResultsFailure() throws Exception {
        Observable<MoviesResponse> observable = Observable.error(new Exception("Network error"));
        when(useCase.requestSearchResults(anyString(), anyBoolean())).thenReturn(observable);

        presenter.requestSearchResults(QUERY, true);
        verify(view).hideError();
        verify(view, times(2)).hideEmpty();
        verify(view).showLoading();

        verify(view).hideLoading();
        verify(view, times(2)).hideData();
        verify(view).showError();
        verify(view, never()).showData();
        verify(view, never()).displaySearchResults(anyObject());
    }
}
