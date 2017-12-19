package com.defaultapps.moviebase.ui.login;


import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.Navigator;

public interface LoginContract {

    interface LoginView extends MvpView {}
    interface LoginNavigator extends Navigator<LoginView> {
        void skipLogin();
        void continueLogin();
    }
}
