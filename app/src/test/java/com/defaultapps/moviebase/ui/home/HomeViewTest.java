package com.defaultapps.moviebase.ui.home;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class HomeViewTest {

    @Test
    public void requestDataOnRefresh() {
        HomeViewImpl homeView = new HomeViewImpl();
        HomePresenterImpl presenter = mock(HomePresenterImpl.class);

        homeView.presenter = presenter;
        homeView.onRefresh();
        verify(presenter).requestMoviesData(true);
    }
}
