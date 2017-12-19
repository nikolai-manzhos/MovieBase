package com.defaultapps.moviebase.ui.login;

import easybind.Layout;
import easybind.bindings.BindNavigator;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.OnClick;

@Layout(id = R.layout.fragment_login, name = "Login")
public class LoginViewImpl extends BaseFragment implements LoginContract.LoginView {

    @BindNavigator
    @Inject
    LoginContract.LoginNavigator loginNavigator;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @OnClick(R.id.login_sign)
    void onSignClick() {
        loginNavigator.continueLogin();
    }

    @OnClick(R.id.login_skip)
    void onSkipClick() {
        loginNavigator.skipLogin();
    }
}
