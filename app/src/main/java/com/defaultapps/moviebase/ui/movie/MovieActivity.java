package com.defaultapps.moviebase.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.common.NavigationView;
import com.defaultapps.moviebase.ui.login.LoginActivity;
import com.defaultapps.moviebase.utils.Utils;
import com.firebase.ui.auth.AuthUI;

import static com.defaultapps.moviebase.utils.AppConstants.MOVIE_ID;
import static com.defaultapps.moviebase.utils.AppConstants.RC_LOGIN;
import static com.defaultapps.moviebase.utils.AppConstants.RC_SIGN_IN;


public class MovieActivity extends BaseActivity implements NavigationView {

    @Override
    protected int provideLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            int movieId = getIntent().getIntExtra(MOVIE_ID, 0);
            replaceMovieFragment(movieId);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "You're signed in!", Toast.LENGTH_SHORT).show();
                replaceMovieFragment(getIntent().getIntExtra(MOVIE_ID, 0));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RC_LOGIN) {
            if (resultCode == RESULT_OK) {
                //redirect to login activity
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTheme(R.style.DarkTheme)
                                .setLogo(R.mipmap.ic_launcher_round)
                                .setProviders(Utils.getProvidersList())
                                .build(),
                        RC_SIGN_IN);
            }
        }
    }

    @Override
    public void displayLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, RC_LOGIN);
    }

    private void replaceMovieFragment(int movieId) {
        MovieViewImpl fragment = MovieViewImpl.newInstance(movieId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit();
    }
}
