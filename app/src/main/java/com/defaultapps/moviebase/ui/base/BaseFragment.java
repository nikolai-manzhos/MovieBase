package com.defaultapps.moviebase.ui.base;

import android.content.Context;
import android.os.Bundle;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(provideLayout(), container, false);
        fragmentComponent = componentActivity.getActivityComponent().plusFragmentComponent();
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @LayoutRes
    protected abstract int provideLayout();

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    protected final FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }
}
