package com.defaultapps.moviebase.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.di.component.DaggerFragmentComponent;
import com.defaultapps.moviebase.di.component.FragmentComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements MvpView {

    private Unbinder unbinder;
    private ComponentActivity componentActivity;
    private FragmentComponent fragmentComponent;

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComponentActivity) {
            componentActivity = (ComponentActivity) context;
        } else {
            throw new IllegalStateException("This activity need to be inherited from BaseActivity");
        }
    }

    @CallSuper
    @Override
    public void onDetach() {
        super.onDetach();
        componentActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(provideLayout(), container, false);
        fragmentComponent = DaggerFragmentComponent.builder()
                .activityComponent(componentActivity.getActivityComponent())
                .build();
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

    @SuppressWarnings("deprecation")
    protected void showSnackbar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    @SuppressWarnings("deprecation")
    protected void showAlertDialog(String title, @Nullable String  message,
                                   DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_ok, listener)
                .setNegativeButton(R.string.alert_cancel, listener)
                .show();
        int accentColor = getResources().getColor(R.color.colorAccent);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(accentColor);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(accentColor);
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    protected FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }
}
