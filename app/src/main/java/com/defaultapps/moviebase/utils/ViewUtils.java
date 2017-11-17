package com.defaultapps.moviebase.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;

import javax.inject.Inject;

@PerActivity
public class ViewUtils {

    private final Context context;
    private final Resources resources;

    @Inject
    public ViewUtils(@ActivityContext Context context) {
        this.context = context;
        resources = context.getResources();
    }

    @SuppressWarnings("deprecation")
    public void showSnackbar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(resources.getColor(R.color.colorPrimary));
        snackbar.show();
    }

    @SuppressWarnings("deprecation")
    public void showAlertDialog(String title, @Nullable String  message,
                                   DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_ok, listener)
                .setNegativeButton(R.string.alert_cancel, listener)
                .show();
        int accentColor = resources.getColor(R.color.colorAccent);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(accentColor);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(accentColor);
    }

    public void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
