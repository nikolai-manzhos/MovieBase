package com.defaultapps.moviebase.ui.home;

import android.content.ComponentName;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.ui.home.adapter.HomeMainAdapter;
import com.defaultapps.moviebase.ui.home.adapter.UpcomingAdapter;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.ui.user.UserActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.ViewUtils;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

public class HomeViewTest extends BaseViewTest {

    @Mock
    private HomePresenterImpl presenter;

    @Mock
    private HomeMainAdapter homeMainAdapter;

    @Mock
    private UpcomingAdapter upcomingAdapter;

    @Mock
    private ViewUtils viewUtils;

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

        TestUtils.addFragmentToFragmentManager(homeView, activity);
    }

    @Test
    public void shouldStartMovieActivityOnMovieClick() {
        final int FAKE_MOVIE_ID = 123;
        homeView.onMovieClick(FAKE_MOVIE_ID);

        ShadowActivity shadowActivity = shadowOf(activity);

        assertEquals(shadowActivity.peekNextStartedActivity().
                        getIntExtra(AppConstants.MOVIE_ID, 0),
                FAKE_MOVIE_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, MovieActivity.class));
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
    public void shouldDisplaySnackbarOnError() {
        final String ERROR_MESSAGE = "Error";
        homeView.displayErrorMessage();
        verify(viewUtils).showSnackbar(homeView.swipeRefreshLayout, ERROR_MESSAGE);
    }
}
