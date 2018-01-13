package com.defaultapps.moviebase.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import easybind.EasyBind;
import easybind.EasyBinder;
import easybind.bindings.BindLayout;
import easybind.bindings.BindName;
import com.defaultapps.moviebase.di.component.FragmentComponent;
import com.defaultapps.moviebase.utils.analytics.Analytics;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements MvpView {

    private Unbinder unbinder;
    private EasyBinder easyBinder;
    private ComponentActivity componentActivity;
    private FragmentComponent fragmentComponent;

    @Inject
    public Analytics analytics;

    @BindName
    public String screenName;

    @BindLayout
    public int layoutId;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentComponent = componentActivity.getActivityComponent().plusFragmentComponent();
        inject();
        easyBinder = EasyBind.bind(this);
        View v = inflater.inflate(layoutId, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        easyBinder.onAttach();
        analytics.sendScreenSelect(screenName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        easyBinder.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing() || !getActivity().isChangingConfigurations()) {
            easyBinder.onDispose();
        }
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @NonNull
    @Override
    public BaseActivity provideActivity() {
        return (BaseActivity) componentActivity;
    }

    protected final FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }

    protected void inject() {}
}
