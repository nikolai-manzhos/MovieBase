package com.defaultapps.moviebase.ui.login;

import android.app.Activity;
import android.content.Intent;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.OnClick;


public class LoginViewImpl extends BaseFragment {


    @Override
    protected int provideLayout() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.login_sign)
    void onSignClick() {
        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    @OnClick(R.id.login_skip)
    void onSkipClick() {
        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        getActivity().finish();
    }
}
