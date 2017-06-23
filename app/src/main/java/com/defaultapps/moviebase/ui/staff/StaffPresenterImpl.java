package com.defaultapps.moviebase.ui.staff;

import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class StaffPresenterImpl extends BasePresenter<StaffContract.StaffView> implements StaffContract.StaffPresenter {

    @Inject
    public StaffPresenterImpl() {

    }
}
