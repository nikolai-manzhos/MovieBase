package com.defaultapps.moviebase.ui.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import com.defaultapps.moviebase.R;


public class BaseFragment extends Fragment {

    @SuppressWarnings("deprecation")
    protected void showSnackbar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
