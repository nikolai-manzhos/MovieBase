package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.TestSchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.ReplaySubject;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class DiscoverUseCaseTest {

    @Mock
    LocalService localService;

    private DiscoverUseCase discoverUseCase;
    private TestScheduler testScheduler;
    private Genres actualResponse;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        discoverUseCase = new DiscoverUseCaseImpl(localService, new TestSchedulerProvider(testScheduler));
    }

    @Test
    public void testGenresDiskProviderSuccess() throws Exception {
        Genres expectedResponse = new Genres();
        Observable<Genres> observable = Observable.just(expectedResponse);
        when(localService.readGenresFromResources()).thenReturn(observable);

        discoverUseCase.provideGenresList().subscribe(
                genres -> actualResponse = genres,
                err -> {}
        );

        testScheduler.triggerActions();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

    }
}
