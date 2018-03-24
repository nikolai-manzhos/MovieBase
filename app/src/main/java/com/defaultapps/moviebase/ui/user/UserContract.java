package com.defaultapps.moviebase.ui.user;


import android.support.annotation.StringRes;

import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.google.firebase.auth.FirebaseUser;

public interface UserContract {
    interface UserPresenter extends MvpPresenter<UserView> {
        void performActionWithAccount();
        void checkUserStatus();
    }

    interface UserView extends MvpView {
        void logoutFromAccount();
        void displayError(@StringRes int stringId);
        void displayNoUserView();
        void displayUserInfoView(FirebaseUser firebaseUser);
        void redirectToAuth();
    }

    interface UserNavigator extends Navigator<UserView> {
        void toAuth();
        void toAboutActivity();
        void logout();
    }
}
