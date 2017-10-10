package com.defaultapps.moviebase.ui.main;

import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;


interface MainContract {

    interface MainView extends MvpView {
        void displayLoginActivity();
    }

    interface MainPresenter extends MvpPresenter<MainView> {
        void checkFirstTimeUser();
    }
}
