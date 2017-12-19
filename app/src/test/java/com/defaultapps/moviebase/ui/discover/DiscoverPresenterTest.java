package com.defaultapps.moviebase.ui.discover;


import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.data.usecase.DiscoverUseCaseImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DiscoverPresenterTest {

    @Mock
    private DiscoverUseCaseImpl useCase;

    @Mock
    private DiscoverContract.DiscoverView view;

    private DiscoverPresenterImpl discoverPresenter;
    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        discoverPresenter = new DiscoverPresenterImpl(useCase);
        discoverPresenter.onAttach(view);
    }

    @Test
    public void requestDataSuccess() throws Exception {
        Genres genres = new Genres();
        Observable<Genres> observable = Observable.just(genres).subscribeOn(testScheduler);
        when(useCase.provideGenresList()).thenReturn(observable);

        discoverPresenter.requestData();
        testScheduler.triggerActions();

        verify(view).showData(genres);
    }

    @Test
    public void requestDataFailure() throws Exception {
        Observable<Genres> observable = Observable.error(new Exception("zzzz"));
        when(useCase.provideGenresList()).thenReturn(observable);

        discoverPresenter.requestData();
        verify(view, never()).showData(any());
    }
}
