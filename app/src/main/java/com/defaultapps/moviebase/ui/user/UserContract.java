package com.defaultapps.moviebase.ui.user;


import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

interface UserContract {
    interface UserPresenter extends MvpPresenter<UserView> {
        void logout();
    }

    interface UserView extends MvpView {
        void logoutFromAccount();
        void displayLogoutError();
    }
}
