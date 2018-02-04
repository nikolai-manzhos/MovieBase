package com.defaultapps.moviebase.ui.search;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.domain.usecase.SearchUseCaseImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchPresenterTest {

    @Mock
    private SearchContract.SearchView view;

    @Mock
    private SearchUseCaseImpl useCase;


    private SearchPresenterImpl presenter;
    private TestScheduler testScheduler;
    private MoviesResponse response;

    private static final String QUERY = "Titanic";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new SearchPresenterImpl(useCase);
        response = random(MoviesResponse.class);
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
        verify(view, never()).displaySearchResults(any(MoviesResponse.class));
    }

    @Test
    public void requestSearchResultsEmpty() {
        MoviesResponse response = random(MoviesResponse.class);
        response.setResults(new LinkedList<>());

        Observable<MoviesResponse> observable = Observable.just(response);
        when(useCase.requestSearchResults(anyString(), anyBoolean())).thenReturn(observable);

        presenter.requestSearchResults(QUERY, true);
        verify(view).hideEmpty();
        verify(view).showLoading();

        verify(view).hideLoading();
        verify(view, times(2)).hideError();
        verify(view).showEmpty();
        verify(view, never()).showData();
        verify(view, never()).displaySearchResults(any(MoviesResponse.class));
    }

    @Test
    public void requestMoreSearchResultsSuccess() {
        when(useCase.requestMoreSearchResults(QUERY)).thenReturn(Observable.just(response).subscribeOn(testScheduler));

        presenter.requestMoreSearchResults(QUERY);

        testScheduler.triggerActions();

        verify(view).displayMoreSearchResults(response);
        verify(view, never()).showLoadMoreError();
    }

    @Test
    public void requestMoreSearchResultsFailure() {
        when(useCase.requestMoreSearchResults(QUERY)).thenReturn(Observable.error(new Exception("Network error.")));

        presenter.requestMoreSearchResults(QUERY);

        verify(view).showLoadMoreError();
        verify(view, never()).displayMoreSearchResults(any(MoviesResponse.class));
    }

    @Test
    public void verifyOnSearchViewClose() {
        presenter.onSearchViewClose();

        verify(view).showSearchStart();
        verify(view).hideLoading();
        verify(view).hideError();
        verify(view).hideData();
        verify(view).hideEmpty();
    }

    @Test
    public void verifyOnSearchViewOpen() {
        presenter.onSearchViewOpen();

        verify(view).hideSearchStart();
    }
}
