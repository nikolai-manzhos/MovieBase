package com.defaultapps.moviebase.ui.staff;


import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

public interface StaffContract {
    interface StaffPresenter extends MvpPresenter<StaffView>{}

    interface StaffView extends MvpView {
        void hideError();
        void showError();
        void hideData();
        void showData();
        void displayStaffInfo();
    }
}
