package com.defaultapps.moviebase.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import easybind.Layout;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;

@Layout(id = R.layout.activity_user)
public class UserActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, new UserViewImpl())
                    .commit();
        }
    }
}
