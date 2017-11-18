package com.defaultapps.moviebase.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.di.component.FragmentComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements MvpView {

    private Unbinder unbinder;
    private MvpPresenter<MvpView> presenter;
    private ComponentActivity componentActivity;
    private FragmentComponent fragmentComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComponentActivity) {
            componentActivity = (ComponentActivity) context;
        } else {
            throw new IllegalStateException("Host activity must implement ComponentActivity interface.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        componentActivity = null;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(provideLayout(), container, false);
        fragmentComponent = componentActivity.getActivityComponent().plusFragmentComponent();
        inject();
        unbinder = ButterKnife.bind(this, v);
        presenter = providePresenter();
        return v;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (presenter != null) presenter.onAttach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (presenter != null) presenter.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing() || !getActivity().isChangingConfigurations()) {
            if (presenter != null) presenter.disposeUseCaseCalls();
        }
    }

    @LayoutRes
    protected abstract int provideLayout();

    protected MvpPresenter providePresenter() {
        return null;
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    protected final FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }

    protected void inject() {}
}
