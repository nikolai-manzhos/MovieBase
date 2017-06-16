package com.defaultapps.moviebase.ui.user;


import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

public interface UserContract {
    interface UserPresenter extends MvpPresenter<UserView> {}

    interface UserView extends MvpView {}
}
