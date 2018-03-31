package com.defaultapps.moviebase.ui.about;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;

import easybind.Layout;

@Layout(id = R.layout.activity_about)
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, new AboutViewImpl())
                    .commit();
        }
    }
}
