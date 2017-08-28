package com.defaultapps.moviebase.ui.home;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
