package com.defaultapps.moviebase.ui.user;

import android.app.Activity;
import android.content.Intent;

import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.about.AboutActivity;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.firebase.ui.auth.AuthUI;

import javax.inject.Inject;


@PerFragment
public class UserNavigatorImpl extends DefaultNavigator<UserContract.UserView> implements UserContract.UserNavigator {

    @Inject
    UserNavigatorImpl() {}

    @Override
    public void toAboutActivity() {
        castToBase().startActivity(new Intent(castToBase(), AboutActivity.class));
    }

    @Override
    public void logout() {
        AuthUI.getInstance().signOut(castToBase());
        finishActivity();
    }

    @Override
    public void toAuth() {
        Intent returnIntent = new Intent();
        castToBase().setResult(Activity.RESULT_OK, returnIntent);
        finishActivity();
    }
}
