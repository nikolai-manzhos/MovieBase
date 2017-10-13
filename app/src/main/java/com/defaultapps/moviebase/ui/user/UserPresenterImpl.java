package com.defaultapps.moviebase.ui.user;

import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;
import com.defaultapps.moviebase.utils.NetworkUtil;

import javax.inject.Inject;

@PerFragment
public class UserPresenterImpl extends BasePresenter<UserContract.UserView> implements UserContract.UserPresenter {

    private final FavoritesManager favoritesManager;
    private final NetworkUtil networkUtil;

    @Inject
    UserPresenterImpl(FavoritesManager favoritesManager, NetworkUtil networkUtil) {
        this.favoritesManager = favoritesManager;
        this.networkUtil = networkUtil;
    }

    @Override
    public void logout() {
        favoritesManager.invalidate();
        if (networkUtil.checkNetworkConnection()) {
            getView().logoutFromAccount();
        } else {
            getView().displayLogoutError();
        }
    }
}
