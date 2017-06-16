package com.defaultapps.moviebase.ui.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;


public class BaseFragment extends Fragment {

    protected void showSnackbar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
