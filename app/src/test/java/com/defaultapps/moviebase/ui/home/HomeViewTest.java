package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.ui.main.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HomeViewTest {

    @Test
    public void requestDataOnRefresh() {
        HomeViewImpl homeView = new HomeViewImpl();
        HomePresenterImpl presenter = mock(HomePresenterImpl.class);

        homeView.presenter = presenter;
        homeView.onRefresh();
        verify(presenter).requestMoviesData(true);
    }

    @Test
    public void shouldDetachPresenterOnDestroyView() {
        HomeViewImpl homeView = new HomeViewImpl();
        SupportFragmentTestUtil.startFragment(homeView, MainActivity.class);
        homeView.presenter = mock(HomePresenterImpl.class);
        homeView.onDestroyView();
        verify(homeView.presenter).onDetach();
    }
}
