package com.defaultapps.moviebase.ui.home;

import android.content.ComponentName;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.ui.home.adapter.HomeMainAdapter;
import com.defaultapps.moviebase.ui.home.adapter.UpcomingAdapter;
import com.defaultapps.moviebase.ui.user.UserActivity;
import com.defaultapps.moviebase.utils.ViewUtils;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

public class HomeViewTest extends BaseRobolectricTest {

    @Mock
    private HomePresenterImpl presenter;

    @Mock
    private HomeMainAdapter homeMainAdapter;

    @Mock
    private UpcomingAdapter upcomingAdapter;

    @Mock
    private ViewUtils viewUtils;

    @Mock
    private Navigator navigator;

    private HomeViewImpl homeView;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        homeView = new HomeViewImpl();
        homeView.presenter = presenter;
        homeView.adapter = homeMainAdapter;
        homeView.upcomingAdapter = upcomingAdapter;
        homeView.viewUtils = viewUtils;
        homeView.navigator = navigator;

        TestUtils.addFragmentToFragmentManager(homeView, activity);
    }

    @Test
    public void shouldStartMovieActivityOnMovieClick() {
        final int FAKE_MOVIE_ID = 123;
        homeView.onMovieClick(FAKE_MOVIE_ID);

        navigator.toMovieActivity(FAKE_MOVIE_ID);
    }

    @Test
    public void shouldCallPresenterOnProfileClick() {
        assert homeView.getView() != null;
        homeView.getView().findViewById(R.id.profileButton).performClick();

        verify(presenter).openProfileScreen();
    }

    @Test
    public void shouldDisplayProfileScreen() {
        homeView.displayProfileScreen();

        ShadowActivity shadowActivity = shadowOf(activity);

        assertEquals(shadowActivity.peekNextStartedActivityForResult().intent.getComponent(),
                new ComponentName(activity, UserActivity.class));
    }

    @Test
    public void requestDataOnRefresh() {
        homeView.onRefresh();
        verify(presenter).requestMoviesData(true);
    }

    @Test
    public void shouldDisplaySnackbarOnErrorWithCurrentData() {
        final String ERROR_MESSAGE = application.getString(R.string.snackbar_error);
        when(homeView.adapter.getItemCount()).thenReturn(1);

        homeView.displayErrorMessage();

        verify(viewUtils).showSnackbar(homeView.swipeRefreshLayout, ERROR_MESSAGE);
    }

    @Test
    public void shouldDisplayErrorViewWithoutCurrentData() {
        when(homeView.adapter.getItemCount()).thenReturn(0);

        homeView.displayErrorMessage();

        verify(viewUtils, never()).showSnackbar(any(View.class), anyString());
    }
}
