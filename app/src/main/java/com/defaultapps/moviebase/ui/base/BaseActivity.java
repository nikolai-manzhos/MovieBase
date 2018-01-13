package com.defaultapps.moviebase.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import easybind.EasyBind;
import easybind.EasyBinder;
import easybind.bindings.BindLayout;
import com.defaultapps.moviebase.App;
import com.defaultapps.moviebase.di.component.ActivityComponent;
import com.defaultapps.moviebase.di.component.DaggerActivityComponent;
import com.defaultapps.moviebase.di.module.ActivityModule;
import com.defaultapps.moviebase.utils.listener.OnBackPressedListener;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity implements ComponentActivity, MvpView {

    protected ActivityComponent activityComponent;
    private OnBackPressedListener onBackPressedListener;

    private EasyBinder easyBinder;
    private Unbinder unbinder;

    @BindLayout
    public int layoutId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getAppComponent())
                .build();
        inject();
        easyBinder = EasyBind.bind(this);
        setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
        easyBinder.onAttach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() || !isChangingConfigurations()) {
            easyBinder.onDispose();
        }
        unbinder.unbind();
        easyBinder.onDetach();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null && onBackPressedListener.onBackPressed()) {
            super.onBackPressed();
        } else if (onBackPressedListener == null) {
            super.onBackPressed();
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    public void hideLoading() {}

    @Override
    public void showLoading() {}

    @NonNull
    @Override
    public BaseActivity provideActivity() {
        return this;
    }

    public void inject() {}
}
