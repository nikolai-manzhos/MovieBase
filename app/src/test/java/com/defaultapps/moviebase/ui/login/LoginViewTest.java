package com.defaultapps.moviebase.ui.login;

import android.app.Activity;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

public class LoginViewTest extends BaseRobolectricTest {

    @Mock
    private LoginContract.LoginNavigator loginNavigator;

    private LoginViewImpl loginView;

    @Override
    public void setup() throws Exception {
        super.setup();
        loginView = new LoginViewImpl();
        loginView.loginNavigator = loginNavigator;

        addFragmentToFragmentManager(loginView, activity);
    }

    @Test
    public void callNavigatorOnSignBtnPress() {
        assert loginView.getView() != null;
        loginView.getView().findViewById(R.id.login_sign).performClick();

        verify(loginNavigator).continueLogin();
    }

    @Test
    public void callNavigatorOnSkipBtnPress() {
        assert loginView.getView() != null;
        loginView.getView().findViewById(R.id.login_skip).performClick();

        verify(loginNavigator).skipLogin();
    }
}
