package com.defaultapps.moviebase.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.utils.AppConstants;


public class MovieActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        String movieId = getIntent().getStringExtra(AppConstants.MOVIE_ID);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.MOVIE_ID, movieId);
        MovieViewImpl fragment = new MovieViewImpl();
        fragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, new MovieViewImpl())
                    .commit();
        }
    }
}
