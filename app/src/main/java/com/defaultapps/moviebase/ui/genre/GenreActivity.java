package com.defaultapps.moviebase.ui.genre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.utils.AppConstants;


public class GenreActivity extends BaseActivity{

    @Override
    protected int provideLayout() {
        return R.layout.activity_genre;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            GenreViewImpl genreView = GenreViewImpl.newInstance(
                    intent.getStringExtra(AppConstants.GENRE_ID),
                    intent.getStringExtra(AppConstants.GENRE_NAME));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, genreView)
                    .commit();
        }
    }
}
