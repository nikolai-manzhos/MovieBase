package com.defaultapps.moviebase.ui.login;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Test;
import org.mockito.Mock;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.TestUtils.setupFakeAnalytics;
import static org.mockito.Mockito.verify;

public class LoginViewTest extends BaseRobolectricTest {

    @Mock
    private LoginContract.LoginNavigator loginNavigator;

    private LoginViewImpl loginView;

    @Override
    public void setup() throws Exception {
        super.setup();
        loginView = new LoginViewImpl();
        loginView.loginNavigator = loginNavigator;

        setupFakeAnalytics(loginView);
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
