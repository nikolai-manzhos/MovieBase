package com.defaultapps.moviebase.ui.common;

import android.content.ComponentName;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.login.LoginActivity;
import com.firebase.ui.auth.KickoffActivity;

import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;


public class DefaultNavigatorTest extends BaseRobolectricTest {

    private DefaultNavigator<MvpView> defaultNavigator;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        defaultNavigator = new DefaultNavigator<>();
        defaultNavigator.onAttach(activity);
    }

    @Test
    public void shouldRedirectToLoginActivity() {
        ShadowActivity shadowActivity = shadowOf(activity);
        defaultNavigator.toLoginActivity();

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, LoginActivity.class));
    }

    @Test
    public void shouldRedirectToSignUpActivity() {
        ShadowActivity shadowActivity = shadowOf(activity);
        defaultNavigator.toSignInActivity();

        assertEquals(shadowActivity.peekNextStartedActivityForResult().intent.getComponent(),
                new ComponentName(activity, KickoffActivity.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNpeAfterDetach() {
        defaultNavigator.onDetach();

        defaultNavigator.toLoginActivity();
    }
}
