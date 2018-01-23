package com.defaultapps.moviebase.ui.movie;


import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.domain.usecase.MovieUseCaseImpl;
import com.defaultapps.moviebase.utils.ResponseOrError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviePresenterTest {

    @Mock
    private MovieContract.MovieView view;

    @Mock
    private MovieUseCaseImpl movieUseCase;

    private MovieContract.MoviePresenter presenter;
    private TestScheduler testScheduler;
    private final int MOCK_MOVIE_ID = 283995;
    private final String POSTER_PATH = "/aJn9XeesqsrSLKcHfHP4u5985hn.jpg";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = new MoviePresenterImpl(movieUseCase);
        presenter.onAttach(view);
    }

    @Test
    public void requestMovieDataSuccess() throws Exception {
        MovieInfoResponse response = new MovieInfoResponse();
        Observable<MovieInfoResponse> observable = Observable.just(response).subscribeOn(testScheduler);
        when(movieUseCase.requestMovieData(anyInt(), anyBoolean())).thenReturn(observable);
        presenter.onAttach(view);

        presenter.requestMovieInfo(1, false);
        verify(view).hideData();
        verify(view).showLoading();

        testScheduler.triggerActions();
        verify(view, times(2)).hideError(); // On requestMovieInfo() and onNext
        verify(view).hideLoading();
        verify(view).displayMovieInfo(response);
        verify(view).showData();
    }

    @Test
    public void requestMovieDataFailure() throws Exception {
        Observable<MovieInfoResponse> observable = Observable.error(new Exception("Some error"));
        when(movieUseCase.requestMovieData(anyInt(), anyBoolean())).thenReturn(observable);

        presenter.requestMovieInfo(MOCK_MOVIE_ID, false);
        verify(view).hideError();
        verify(view).showLoading();

        verify(view).hideLoading();
        verify(view, times(2)).hideData(); // On requestMovieInfo() and onError
        verify(view).showError();
        verify(view, never()).showData();
        verify(view, never()).displayMovieInfo(any(MovieInfoResponse.class));
    }

    @Test
    public void addToFavSuccess() throws Exception {
        Observable<ResponseOrError<Boolean>> observable = Observable.just(ResponseOrError.fromData(true)).subscribeOn(testScheduler);
        when(movieUseCase.addOrRemoveFromDatabase(anyInt(), anyString())).thenReturn(observable);
        when(movieUseCase.getUserState()).thenReturn(true);

        presenter.addOrRemoveFromFavorites(MOCK_MOVIE_ID, POSTER_PATH);
        testScheduler.triggerActions();

        verify(view, never()).displayTransactionError();
    }

    @Test
    public void addToFavFailure() throws Exception {
        Observable<ResponseOrError<Boolean>> single = Observable.just(ResponseOrError.fromError("Network error."));
        when(movieUseCase.addOrRemoveFromDatabase(anyInt(), anyString())).thenReturn(single);
        when(movieUseCase.getUserState()).thenReturn(true);

        presenter.addOrRemoveFromFavorites(MOCK_MOVIE_ID, POSTER_PATH);

        verify(view).displayTransactionError();
    }

    @Test
    public void shouldDisplayLoginActivityOnNoUser() {
        when(movieUseCase.getUserState()).thenReturn(false);

        presenter.addOrRemoveFromFavorites(MOCK_MOVIE_ID, POSTER_PATH);

        verify(view).displayLoginScreen();
    }

    @Test
    public void requestFavoriteStatusSuccess() throws Exception {
        Observable<Boolean> observable = Observable.just(true).subscribeOn(testScheduler);
        when(movieUseCase.getCurrentState(anyInt())).thenReturn(observable);

        presenter.requestFavoriteStatus(MOCK_MOVIE_ID);
        testScheduler.triggerActions();

        verify(view).setFabStatus(anyBoolean());
    }
}
