package com.defaultapps.moviebase.ui.genre;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.usecase.GenreUseCaseImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenrePresenterTest {

    @Mock
    GenreUseCaseImpl useCase;

    @Mock
    GenreContract.GenreView view;

    private GenreContract.GenrePresenter presenter;
    private TestScheduler testScheduler;

    private final String FAKE_MOVIE_ID = "11321";
    private final boolean FORCE = true;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = new GenrePresenterImpl(useCase);
        presenter.onAttach(view);
    }

    @Test
    public void requestGenreDataSuccess() throws Exception {
        MoviesResponse movies = new MoviesResponse();
        Observable<MoviesResponse> observable = Observable.just(movies).subscribeOn(testScheduler);
        when(useCase.requestGenreData(anyString(), anyBoolean())).thenReturn(observable);

        presenter.requestMovies(FAKE_MOVIE_ID, FORCE);

        verify(view).showLoading();

        testScheduler.triggerActions();

        verify(view).hideLoading();
        verify(view, times(2)).hideError(); // on presenter.requestMovies() and onNext()
        verify(view).showMovies(movies);
    }

    @Test
    public void requestGenreDataFailure() throws Exception {
        Observable<MoviesResponse> observable = Observable.error(new Exception("Some error"));
        when(useCase.requestGenreData(anyString(), anyBoolean())).thenReturn(observable);

        presenter.requestMovies(FAKE_MOVIE_ID, FORCE);
        verify(view).showLoading();
        verify(view).hideError();

        testScheduler.triggerActions();

        verify(view).hideLoading();
        verify(view).showError();
    }

    @Test
    public void requestMoreMoviesSuccess() throws Exception {
        MoviesResponse response = new MoviesResponse();
        Observable<MoviesResponse> observable = Observable.just(response).subscribeOn(testScheduler);
        when(useCase.requestMoreGenreData(anyString())).thenReturn(observable);

        presenter.requestMoreMovies(FAKE_MOVIE_ID);
        testScheduler.triggerActions();

        verify(view).showMoreMovies(response);
        verify(view, never()).showLoadMoreError();
    }

    @Test
    public void requestMoreMoviesFailure() throws Exception {
        Observable<MoviesResponse> observable = Observable.error(new Exception("Some error."));
        when(useCase.requestMoreGenreData(anyString())).thenReturn(observable);

        presenter.requestMoreMovies(FAKE_MOVIE_ID);

        verify(view).showLoadMoreError();
        verify(view, never()).showMoreMovies(any(MoviesResponse.class));
    }
}
