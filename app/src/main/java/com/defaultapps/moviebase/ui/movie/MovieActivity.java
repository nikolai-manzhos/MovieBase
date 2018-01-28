package com.defaultapps.moviebase.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.base.Navigator;

import javax.inject.Inject;

import easybind.Layout;
import easybind.bindings.BindNavigator;

import static com.defaultapps.moviebase.utils.AppConstants.MOVIE_ID;
import static com.defaultapps.moviebase.utils.AppConstants.RC_LOGIN;
import static com.defaultapps.moviebase.utils.AppConstants.RC_SIGN_IN;

@Layout(id = R.layout.activity_movie)
public class MovieActivity extends BaseActivity {

    @BindNavigator
    @ActivityContext
    @Inject
    Navigator navigator;

    @Override
    public void inject() {
        getActivityComponent().inject(this);
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
                navigator.toSignInActivity();
            }
        }
    }

    private void replaceMovieFragment(int movieId) {
        MovieViewImpl fragment = MovieViewImpl.newInstance(movieId);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit();
    }
}
