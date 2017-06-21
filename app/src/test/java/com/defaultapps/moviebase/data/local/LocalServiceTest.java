package com.defaultapps.moviebase.data.local;


import android.content.res.AssetManager;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class LocalServiceTest {

    @Mock
    AssetManager assetManager;

    private LocalService localService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        localService = new LocalService(assetManager);
    }

    @Test
    public void readGenresFromResourcesSuccess() throws Exception {
        String file = "genres.json";
        when(assetManager.open("genres.json")).thenReturn(getClass().getClassLoader().getResourceAsStream(file));
        Genres genres = localService.readGenresFromResources();

        assertNotNull(genres.getGenres());
    }
}
