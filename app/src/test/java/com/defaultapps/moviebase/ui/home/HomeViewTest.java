package com.defaultapps.moviebase.ui.home;

import android.content.ComponentName;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.TestUtils;
import com.defaultapps.moviebase.ui.home.adapter.HomeMainAdapter;
import com.defaultapps.moviebase.ui.home.adapter.UpcomingAdapter;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.ui.user.UserActivity;
import com.defaultapps.moviebase.utils.AppConstants;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

public class HomeViewTest extends BaseViewTest {

    @Mock
    private HomePresenterImpl presenter;

    @Mock
    private HomeMainAdapter homeMainAdapter;

    @Mock
    private UpcomingAdapter upcomingAdapter;

    private HomeViewImpl homeView;

    @Override
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        MockitoAnnotations.initMocks(this);
        homeView = new HomeViewImpl();
        homeView.presenter = presenter;
        homeView.adapter = homeMainAdapter;
        homeView.upcomingAdapter = upcomingAdapter;

        TestUtils.addFragmentToFragmentManager(homeView, activity, R.id.contentFrame);
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
        HomeViewImpl homeView = new HomeViewImpl();
        HomePresenterImpl presenter = mock(HomePresenterImpl.class);

        homeView.presenter = presenter;
        homeView.onRefresh();
        verify(presenter).requestMoviesData(true);
    }

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_main;
    }
}
