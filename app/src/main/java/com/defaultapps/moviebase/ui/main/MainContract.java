package com.defaultapps.moviebase.ui.main;

import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;


public interface MainContract {
    interface MainPresenter extends MvpPresenter<MainView> {
        void checkFirstTimeUser();
    }

    interface MainView extends MvpView {
        void displayLoginActivity();
    }
}
