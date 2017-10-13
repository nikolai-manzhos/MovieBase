package com.defaultapps.moviebase.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.defaultapps.moviebase.di.ApplicationContext;

import javax.inject.Inject;

public class NetworkUtil {

    private final Context context;

    @Inject
    NetworkUtil(@ApplicationContext Context context) {
        this.context = context;
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
