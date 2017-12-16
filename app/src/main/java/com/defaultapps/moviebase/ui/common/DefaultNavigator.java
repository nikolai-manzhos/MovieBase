package com.defaultapps.moviebase.ui.common;

import android.content.Intent;
import android.support.annotation.CallSuper;

import com.defaultapps.easybind.NavigatorClass;
import com.defaultapps.easybind.calls.OnAttach;
import com.defaultapps.easybind.calls.OnDetach;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.ui.login.LoginActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.Utils;
import com.firebase.ui.auth.AuthUI;

import static com.defaultapps.moviebase.utils.AppConstants.RC_SIGN_IN;

@NavigatorClass
public class DefaultNavigator<V extends MvpView> implements Navigator<V> {

    public DefaultNavigator() {}

    private V view;

    @OnAttach
    @CallSuper
    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @OnDetach
    @CallSuper
    @Override
    public void onDetach() {
        view = null;
    }

    private BaseActivity castToBase() {
        return view.provideActivity();
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(castToBase(), LoginActivity.class);
        castToBase().startActivityForResult(intent, AppConstants.RC_LOGIN);
    }

    @Override
    public void toSignInActivity() {
        castToBase().startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.DarkTheme)
                        .setLogo(R.mipmap.ic_launcher_round)
                        .setAvailableProviders(Utils.getProvidersList())
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void toMovieActivity(int movieId) {
        Intent intent = new Intent(castToBase(), MovieActivity.class)
                .putExtra(AppConstants.MOVIE_ID, movieId);
        castToBase().startActivity(intent);
    }

    @Override
    public void finishActivity() {
        castToBase().finish();
    }
}
