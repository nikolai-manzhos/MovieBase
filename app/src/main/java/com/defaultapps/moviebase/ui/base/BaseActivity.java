package com.defaultapps.moviebase.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.defaultapps.moviebase.App;
import com.defaultapps.moviebase.di.component.ActivityComponent;
import com.defaultapps.moviebase.di.component.DaggerActivityComponent;
import com.defaultapps.moviebase.di.module.ActivityModule;
import com.defaultapps.moviebase.utils.listener.OnBackPressedListener;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements ComponentActivity, MvpView {

    protected ActivityComponent activityComponent;
    private MvpPresenter<MvpView> presenter;
    private OnBackPressedListener onBackPressedListener;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((App) getApplication()).getAppComponent())
                .build();
        inject();
        setContentView(provideLayout());
        ButterKnife.bind(this);
        presenter = providePresenter();
        if (presenter != null) presenter.onAttach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDetach();
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

    protected MvpPresenter providePresenter() {
        return null;
    }

    @LayoutRes
    protected abstract int provideLayout();

    public void inject() {}
}
