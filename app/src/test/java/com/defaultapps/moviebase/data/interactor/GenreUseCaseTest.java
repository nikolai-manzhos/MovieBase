package com.defaultapps.moviebase.data.interactor;

import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.assertTrue;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

public class    GenreUseCaseTest {

    @Mock
    LocalService localService;

    private DiscoverUseCase discoverUseCase;
    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        discoverUseCase = new DiscoverUseCaseImpl(localService, new TestSchedulerProvider(testScheduler));
    }

    @Test
    public void testGenresDiskProvider() throws Exception {
        Genres genres = new Genres();
        when(localService.readGenresFromResources()).thenReturn(genres);

        Observable<Genres> observable = discoverUseCase.provideGenresList();
        assertTrue(observable instanceof ReplaySubject);
    }
}