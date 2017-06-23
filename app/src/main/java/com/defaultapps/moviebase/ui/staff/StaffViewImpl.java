package com.defaultapps.moviebase.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StaffViewImpl extends BaseFragment implements StaffContract.StaffView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    private Unbinder unbinder;

    public static StaffViewImpl createInstance(int staffKind, int staffId) {
        StaffViewImpl staffView = new StaffViewImpl();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.CREW_OR_CAST, staffKind);
        bundle.putInt(AppConstants.STAFF_ID, staffId);
        staffView.setArguments(bundle);
        return staffView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        ((StaffActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }
}
