package com.defaultapps.moviebase.ui.user;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.firebase.LoggedUser;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.firebase.ui.auth.AuthUI;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.squareup.picasso.Picasso;
import com.yarolegovich.mp.MaterialStandardPreference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewImpl extends BaseFragment implements UserContract.UserView {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.logout)
    MaterialStandardPreference logoutButton;

    @BindView(R.id.userAvatar)
    CircleImageView userAvatar;

    @Inject
    LoggedUser loggedUser;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((UserActivity) getActivity()).getActivityComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);

        setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.logout)
    void onLogoutClick() {
        if (((UserActivity) getActivity()).checkNetworkConnection()) {
            AuthUI.getInstance().signOut(getActivity());
            getActivity().finish();
        } else {
            showSnackbar(contentContainer, getString(R.string.user_logout_error));
        }
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void hideLoading() {}

    @Override
    public void showLoading() {}

    private void setupViews() {
        logoutButton.setSummary(getString(R.string.user_logged_as) + " "
                + loggedUser.getFirebaseuser().getDisplayName());
        Picasso
                .with(getContext())
                .load(loggedUser.getFirebaseuser().getPhotoUrl())
                .into(userAvatar);
    }
}
