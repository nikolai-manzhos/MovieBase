package com.defaultapps.moviebase.ui.user;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.LinearLayout;

import easybind.Layout;
import easybind.bindings.BindPresenter;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.user.UserContract.UserPresenter;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.yarolegovich.mp.MaterialStandardPreference;
import com.yarolegovich.mp.MaterialSwitchPreference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

@Layout(id = R.layout.fragment_user, name = "User")
public class UserViewImpl extends BaseFragment implements UserContract.UserView {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.accountBtn)
    MaterialStandardPreference accountButton;

    @BindView(R.id.adultSwitch)
    MaterialSwitchPreference adultSwitch;

    @BindView(R.id.userAvatar)
    CircleImageView userAvatar;

    @BindPresenter
    @Inject
    UserPresenter presenter;

    @Inject
    ViewUtils viewUtils;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.checkUserStatus();
    }

    @OnClick(R.id.accountBtn)
    void onLogoutClick() {
        presenter.performActionWithAccount();
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        getActivity().finish();
    }

    @Override
    public void logoutFromAccount() {
        AuthUI.getInstance().signOut(getActivity());
        getActivity().finish();
    }

    @Override
    public void displayError(@StringRes int stringId) {
        viewUtils.showSnackbar(contentContainer, getString(stringId));
    }

    @Override
    public void displayNoUserView() {
        accountButton.setTitle(getString(R.string.user_login));
        Picasso
                .with(getContext())
                .load(R.drawable.placeholder_human)
                .into(userAvatar);
    }

    @Override
    public void displayUserInfoView(FirebaseUser firebaseUser) {
        accountButton.setSummary(getString(R.string.user_logged_as) + " "
                + firebaseUser.getDisplayName());
        Picasso
                .with(getContext())
                .load(firebaseUser.getPhotoUrl())
                .placeholder(R.drawable.placeholder_human)
                .into(userAvatar);
    }

    @Override
    public void redirectToAuth() {
        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }
}
