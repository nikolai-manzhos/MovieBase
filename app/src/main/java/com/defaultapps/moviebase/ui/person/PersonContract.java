package com.defaultapps.moviebase.ui.person;


import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

public interface PersonContract {
    interface PersonPresenter extends MvpPresenter<PersonView>{
        void requestPersonInfo(int personId, boolean force);
    }

    interface PersonView extends MvpView {
        void hideError();
        void showError();
        void hideData();
        void showData();
        void displayStaffInfo(PersonInfo personInfo);
    }
}
