package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.interactor.HomeUseCaseImpl;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class HomePresenterTest {

    @Mock
    HomeUseCaseImpl homeUseCase;

    @Mock
    HomeContract.HomeView view;

    private HomeContract.HomePresenter presenter;
    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new HomePresenterImpl(homeUseCase);
        testScheduler = new TestScheduler();
    }

    @Test
    public void requestHomeDataSuccess() throws Exception {
        List<MoviesResponse> response = new ArrayList<>();
        Observable<List<MoviesResponse>> resultObservable = Observable.just(response).subscribeOn(testScheduler);
        when(homeUseCase.requestHomeData(anyBoolean())).thenReturn(resultObservable);
        presenter.onAttach(view);

        presenter.requestMoviesData(true);

        verify(view).showLoading();

        testScheduler.triggerActions();

        verify(view).hideLoading();
        verify(view).receiveResults(response);
    }

    @Test
    public void requestHomeDataFailure() throws Exception {
        when(homeUseCase.requestHomeData(anyBoolean())).thenReturn(Observable.error(new Exception("Some error.")));

        presenter.onAttach(view);
        presenter.requestMoviesData(true);

        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).receiveResults(anyListOf(MoviesResponse.class));
    }
}
