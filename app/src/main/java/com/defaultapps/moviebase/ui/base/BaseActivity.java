package com.defaultapps.moviebase.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.defaultapps.moviebase.App;
import com.defaultapps.moviebase.di.component.ActivityComponent;
import com.defaultapps.moviebase.di.component.DaggerActivityComponent;
import com.defaultapps.moviebase.di.module.ActivityModule;


public class BaseActivity extends AppCompatActivity implements ComponentActivity, MvpView {
    protected ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getAppComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public void hideLoading() {}

    @Override
    public void showLoading() {}
}
