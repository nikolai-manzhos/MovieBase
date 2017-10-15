package com.defaultapps.moviebase.ui.main;

import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;
import com.defaultapps.moviebase.ui.common.NavigationView;

import javax.inject.Inject;

@PerActivity
public class MainPresenterImpl extends BasePresenter<NavigationView>
        implements MainContract.MainPresenter {

    private final AppPreferencesManager preferencesManager;

    @Inject
    MainPresenterImpl(AppPreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    @Override
    public void checkFirstTimeUser() {
        if (preferencesManager.getFirstTimeUser()) {
            getView().displayLoginActivity();
            preferencesManager.setFirstTimeUser(false);
        }
    }
}
