package com.defaultapps.moviebase.ui.movie;


import com.defaultapps.moviebase.data.usecase.MovieUseCaseImpl;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviePresenterTest {

    @Mock
    MovieContract.MovieView view;

    @Mock
    MovieUseCaseImpl movieUseCase;

    private MovieContract.MoviePresenter presenter;
    private TestScheduler testScheduler;

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

        presenter.requestMovieInfo(1, false);
        verify(view).hideError();
        verify(view).showLoading();

        verify(view).hideLoading();
        verify(view, times(2)).hideData(); // On requestMovieInfo() and onError
        verify(view).showError();
        verify(view, never()).showData();
        verify(view, never()).displayMovieInfo(any(MovieInfoResponse.class));
    }
// TODO: redo test for for favorite
//    @Test
//    public void addToFavSuccess() throws Exception {
//        Observable<Boolean> single = Observable.just(true).subscribeOn(testScheduler);
//        when(movieUseCase.addOrRemoveFromDatabase(anyInt(), anyString())).thenReturn();
//
//        presenter.addOrRemoveFromFavorites(283995, "/aJn9XeesqsrSLKcHfHP4u5985hn.jpg");
//        testScheduler.triggerActions();
//
//        verify(view).displayTransactionError(true);
//        verify(view, never()).displayTransactionError(false);
//    }
//
//    @Test
//    public void addToFavFailure() throws Exception {
//        Single<Boolean> single = Single.error(new Exception("Error while accessing database"));
//        when(movieUseCase.addOrRemoveFromDatabase(anyInt(), anyString())).thenReturn(single);
//
//        presenter.addOrRemoveFromFavorites(283995, "/aJn9XeesqsrSLKcHfHP4u5985hn.jpg");
//
//        verify(view).displayTransactionError(false);
//        verify(view, never()).displayTransactionError(true);
//    }
}
