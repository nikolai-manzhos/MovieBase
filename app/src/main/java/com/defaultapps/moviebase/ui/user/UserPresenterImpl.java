package com.defaultapps.moviebase.ui.user;

import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;
import com.defaultapps.moviebase.utils.NetworkUtil;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

@PerFragment
public class UserPresenterImpl extends BasePresenter<UserContract.UserView> implements UserContract.UserPresenter {

    private final FavoritesManager favoritesManager;
    private final NetworkUtil networkUtil;
    private final FirebaseUser firebaseUser;

    @Inject
    UserPresenterImpl(FavoritesManager favoritesManager,
                      NetworkUtil networkUtil,
                      @Nullable FirebaseUser firebaseUser) {
        this.favoritesManager = favoritesManager;
        this.networkUtil = networkUtil;
        this.firebaseUser = firebaseUser;
    }

    @Override
    public void performActionWithAccount() {
        favoritesManager.invalidate();
        if (firebaseUser == null) {
            getView().redirectToAuth();
        } else if (networkUtil.checkNetworkConnection()) {
            getView().logoutFromAccount();
        } else {
            getView().displayError(R.string.user_logout_error);
        }
    }

    @Override
    public void checkUserStatus() {
        if (firebaseUser != null) {
            getView().displayUserInfoView(firebaseUser);
        } else {
            getView().displayNoUserView();
        }
    }
}
