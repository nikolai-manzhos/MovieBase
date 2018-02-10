package com.defaultapps.moviebase.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

import com.defaultapps.moviebase.R;
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

    public String getString(@StringRes int id) {
        return context.getString(id);
    }

    public int convertPxToDp(int px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int convertDpToPx(int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @DrawableRes
    public int resolveGenreIconId(int id) {
        switch (id) {
            case 28:
                return R.drawable.ic_action;
            case 12:
                return R.drawable.ic_adventure;
            case 16:
                return R.drawable.ic_animation;
            case 35:
                return R.drawable.ic_comedy;
            case 80:
                return R.drawable.ic_crime;
            case 99:
                return R.drawable.ic_documentary;
            case 18:
                return R.drawable.ic_drama;
            case 10751:
                return R.drawable.ic_family;
            case 14:
                return R.drawable.ic_fantasy;
            case 36:
                return R.drawable.ic_history;
            case 27:
                return R.drawable.ic_horror;
            case 10402:
                return R.drawable.ic_music;
            case 9648:
                return R.drawable.ic_mystery;
            case 10749:
                return R.drawable.ic_romance;
            case 878:
                return R.drawable.ic_science_fiction;
            case 10770:
                return R.drawable.ic_tv_movie;
            case 53:
                return R.drawable.ic_thriller;
            case 10752:
                return R.drawable.ic_war;
            case 37:
                return R.drawable.ic_western;
            default:
                throw new IllegalArgumentException("Supply correct genre id.");
        }
    }
}
