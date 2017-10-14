package com.defaultapps.moviebase.ui.user;



import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @BindView(R.id.logout)
    MaterialStandardPreference logoutButton;

    @BindView(R.id.adultSwitch)
    MaterialSwitchPreference adultSwitch;

    @BindView(R.id.userAvatar)
    CircleImageView userAvatar;

    @Inject
    UserPresenterImpl presenter;

    @Inject
    @Nullable
    FirebaseUser loggedUser;

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
        if (loggedUser != null) setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.logout)
    void onLogoutClick() {
        presenter.logout();
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        getActivity().onBackPressed();
    }

    private void setupViews() {
        logoutButton.setSummary(getString(R.string.user_logged_as) + " "
                + loggedUser.getDisplayName());
        Picasso
                .with(getContext())
                .load(loggedUser.getPhotoUrl())
                .placeholder(R.drawable.placeholder_human)
                .into(userAvatar);
    }

    @Override
    public void logoutFromAccount() {
        AuthUI.getInstance().signOut(getActivity());
        getActivity().finish();
    }

    @Override
    public void displayLogoutError() {
        viewUtils.showSnackbar(contentContainer, getString(R.string.user_logout_error));
    }
}
