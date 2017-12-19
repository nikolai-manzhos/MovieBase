package com.defaultapps.moviebase.ui.login;

import android.app.Activity;
import android.content.Intent;

import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;

import javax.inject.Inject;

@PerFragment
public class LoginNavigatorImpl extends DefaultNavigator<LoginContract.LoginView> implements LoginContract.LoginNavigator {

    @Inject
    LoginNavigatorImpl() {    }

    @Override
    public void skipLogin() {
        Intent returnIntent = new Intent();
        castToBase().setResult(Activity.RESULT_CANCELED, returnIntent);
        castToBase().finish();
    }

    @Override
    public void continueLogin() {
        Intent returnIntent = new Intent();
        castToBase().setResult(Activity.RESULT_OK, returnIntent);
        castToBase().finish();
    }
}
