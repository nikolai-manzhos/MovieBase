package com.defaultapps.moviebase.ui.main;

import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.common.NavigationView;


public interface MainContract {
    interface MainPresenter extends MvpPresenter<NavigationView> {
        void checkFirstTimeUser();
    }
}
