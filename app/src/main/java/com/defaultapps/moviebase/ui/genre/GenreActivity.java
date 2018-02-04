package com.defaultapps.moviebase.ui.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.utils.AppConstants;

import easybind.Layout;

@Layout(id = R.layout.activity_genre)
public class GenreActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            GenreViewImpl genreView = GenreViewImpl.newInstance(
                    getIntent().getStringExtra(AppConstants.GENRE_ID),
                    getIntent().getStringExtra(AppConstants.GENRE_NAME));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, genreView)
                    .commit();
        }
    }
}
