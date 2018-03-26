package com.defaultapps.moviebase.ui.user;

import android.content.ComponentName;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.about.AboutActivity;

import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;


public class UserNavigatorTest extends BaseRobolectricTest {

    @Mock
    private UserContract.UserView userView;

    private UserContract.UserNavigator userNavigator;
    private ShadowActivity shadowActivity;

    @Override
    public void setup() throws Exception {
        super.setup();
        userNavigator = new UserNavigatorImpl();
        userNavigator.onAttach(userView);
        when(userView.provideActivity()).thenReturn(activity);
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void redirectToAboutActivity() {
        userNavigator.toAboutActivity();

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, AboutActivity.class));
    }

    @Test
    public void closeActivityOnLogout() {
        userNavigator.logout();

        assertTrue(shadowActivity.isFinishing());
    }
}
