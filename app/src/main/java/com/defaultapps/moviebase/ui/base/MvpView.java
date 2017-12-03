package com.defaultapps.moviebase.ui.base;

import android.support.annotation.NonNull;

public interface MvpView {
    void showLoading();
    void hideLoading();
    @NonNull BaseActivity provideActivity();
}
