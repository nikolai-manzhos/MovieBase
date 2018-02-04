package com.defaultapps.moviebase.ui.login;


import android.app.Activity;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

public class LoginNavigatorTest extends BaseRobolectricTest {

    @Mock
    private LoginContract.LoginView view;

    private LoginContract.LoginNavigator loginNavigator;
    private ShadowActivity shadowActivity;

    @Override
    public void setup() throws Exception {
        super.setup();
        loginNavigator = new LoginNavigatorImpl();
        loginNavigator.onAttach(view);
        when(view.provideActivity()).thenReturn(activity);
        shadowActivity = shadowOf(activity);
    }

    @Test
    public void finishActivityWithResultOk() {
        loginNavigator.continueLogin();

        assertEquals(Activity.RESULT_OK, shadowActivity.getResultCode());
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void finishActivityWithResultCancel() {
        loginNavigator.skipLogin();

        assertEquals(Activity.RESULT_CANCELED, shadowActivity.getResultCode());
        assertTrue(shadowActivity.isFinishing());
    }
}
