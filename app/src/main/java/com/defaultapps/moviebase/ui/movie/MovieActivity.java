package com.defaultapps.moviebase.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.common.NavigationView;
import com.defaultapps.moviebase.ui.login.LoginActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.Utils;
import com.firebase.ui.auth.AuthUI;


public class MovieActivity extends BaseActivity implements NavigationView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        if (savedInstanceState == null) {
            int movieId = getIntent().getIntExtra(AppConstants.MOVIE_ID, 0);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.MOVIE_ID, movieId);
            MovieViewImpl fragment = new MovieViewImpl();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, fragment)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "You're signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == AppConstants.RC_LOGIN) {
            if (resultCode == RESULT_OK) {
                //redirect to login activity
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTheme(R.style.DarkTheme)
                                .setLogo(R.mipmap.ic_launcher_round)
                                .setProviders(Utils.getProvidersList())
                                .build(),
                        AppConstants.RC_SIGN_IN);
            }
        }
    }

    @Override
    public void displayLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, AppConstants.RC_LOGIN);
    }
}
