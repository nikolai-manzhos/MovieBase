package com.defaultapps.moviebase.ui.user;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.LinearLayout;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;
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

public class UserViewImpl extends BaseFragment implements UserContract.UserView {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.accountBtn)
    MaterialStandardPreference accountButton;

    @BindView(R.id.adultSwitch)
    MaterialSwitchPreference adultSwitch;

    @BindView(R.id.userAvatar)
    CircleImageView userAvatar;

    @Inject
    UserPresenterImpl presenter;

    @Inject
    ViewUtils viewUtils;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        presenter.onAttach(this);
        presenter.checkUserStatus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }

    @OnClick(R.id.accountBtn)
    void onLogoutClick() {
        presenter.performActionWithAccount();
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        getActivity().onBackPressed();
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
