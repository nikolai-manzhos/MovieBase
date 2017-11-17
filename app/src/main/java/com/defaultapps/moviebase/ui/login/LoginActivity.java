package com.defaultapps.moviebase.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;


public class LoginActivity extends BaseActivity {

    @Override
    protected int provideLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, new LoginViewImpl())
                    .commit();
        }
    }
}
