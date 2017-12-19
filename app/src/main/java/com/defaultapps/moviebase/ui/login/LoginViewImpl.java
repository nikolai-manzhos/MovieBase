package com.defaultapps.moviebase.ui.login;

import android.app.Activity;
import android.content.Intent;

import com.defaultapps.easybind.Layout;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;

import butterknife.OnClick;

@Layout(id = R.layout.fragment_login, name = "Login")
public class LoginViewImpl extends BaseFragment {

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
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
        getActivity().finish();
    }
}
