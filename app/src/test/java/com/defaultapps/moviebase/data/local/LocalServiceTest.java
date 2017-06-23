package com.defaultapps.moviebase.data.local;


import android.content.res.AssetManager;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class LocalServiceTest {

    @Mock
    AssetManager assetManager;

    private LocalService localService;
    private TestScheduler testScheduler;
    private Genres result;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        localService = new LocalService(assetManager);
    }

    @Test
    public void readGenresFromResourcesSuccess() throws Exception {
        String file = "genres.json";
        when(assetManager.open("genres.json")).thenReturn(getClass().getClassLoader().getResourceAsStream(file));
        Observable<Genres> observable = localService.readGenresFromResources().subscribeOn(testScheduler);

        observable.subscribe(
                genres -> result = genres,
                err -> {}
        );

        testScheduler.triggerActions();

        assertNotNull(result);

    }
}
