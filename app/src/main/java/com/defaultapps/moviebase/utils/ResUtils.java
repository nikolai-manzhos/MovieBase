package com.defaultapps.moviebase.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.defaultapps.moviebase.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
@SuppressWarnings("unused")
public class ResUtils {

    private final Context context;

    @Inject
    ResUtils(@ApplicationContext Context context) {
        this.context = context;
    }

    public int convertPxToDp(int px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int convertDpToPx(int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int)(dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
