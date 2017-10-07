package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.usecase.HomeUseCaseImpl;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.TrampolineBusThreadScheduler;
import com.defaultapps.moviebase.utils.rx.RxBus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
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
    private RxBus rxBus;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        rxBus = new RxBus(new TrampolineBusThreadScheduler());
        presenter = new HomePresenterImpl(homeUseCase, rxBus);
        presenter.onAttach(view);
        testScheduler = new TestScheduler();
    }

    @Test
    public void requestHomeDataSuccess() throws Exception {
        List<MoviesResponse> response = new ArrayList<>();
        Observable<List<MoviesResponse>> resultObservable = Observable.just(response).subscribeOn(testScheduler);
        when(homeUseCase.requestHomeData(anyBoolean())).thenReturn(resultObservable);

        presenter.requestMoviesData(true);

        verify(view).showLoading();

        testScheduler.triggerActions();

        verify(view).hideLoading();
        verify(view).receiveResults(response);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void requestHomeDataFailure() throws Exception {
        when(homeUseCase.requestHomeData(anyBoolean())).thenReturn(Observable.error(new Exception("Some error.")));

        presenter.requestMoviesData(true);

        verify(view).showLoading();
        verify(view).hideLoading();
        verify(view, never()).receiveResults(ArgumentMatchers.anyListOf(MoviesResponse.class));
    }

    @Test
    public void triggerInstantCache() throws Exception {
        List<MoviesResponse> responses = new LinkedList<>();
        rxBus.publish(AppConstants.HOME_INSTANT_CACHE, responses);

        verify(view).receiveResults(responses);
    }

    @Test
    public void shouldCallOnComplete() throws Exception {
        InOrder orderVerifier = inOrder(view);
        when(view.isRefreshing())
                .thenReturn(true);
        when(homeUseCase.requestHomeData(false))
                .thenReturn(Observable.empty());

        presenter.requestMoviesData(false);

        orderVerifier.verify(view).showLoading();
        orderVerifier.verify(view).hideLoading();
    }

    @Test
    public void unsubscribeOnDetach() throws Exception {
        rxBus = mock(RxBus.class);
        presenter = new HomePresenterImpl(homeUseCase, rxBus);

        presenter.onDetach();

        verify(rxBus).unsubscribe(presenter);
    }
}
