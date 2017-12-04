package com.defaultapps.moviebase.ui.common;

import android.content.ComponentName;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.login.LoginActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.firebase.ui.auth.KickoffActivity;

import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;


public class DefaultNavigatorTest extends BaseRobolectricTest {

    private DefaultNavigator<MvpView> defaultNavigator;
    private ShadowActivity shadowActivity;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        defaultNavigator = new DefaultNavigator<>();
        defaultNavigator.onAttach(activity);
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void shouldRedirectToLoginActivity() {
        defaultNavigator.toLoginActivity();

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, LoginActivity.class));
    }

    @Test
    public void shouldRedirectToSignUpActivity() {
        defaultNavigator.toSignInActivity();

        assertEquals(shadowActivity.peekNextStartedActivityForResult().intent.getComponent(),
                new ComponentName(activity, KickoffActivity.class));
    }

    @Test
    public void shouldRedirectToMovieActivity() {
        final int ANY_MOVIE_ID = 2313151;
        defaultNavigator.toMovieActivity(ANY_MOVIE_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, MovieActivity.class));
        assertEquals(shadowActivity.peekNextStartedActivity().getIntExtra(AppConstants.MOVIE_ID, 0),
                ANY_MOVIE_ID);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNpeAfterDetach() {
        defaultNavigator.onDetach();

        defaultNavigator.toLoginActivity();
    }
}
