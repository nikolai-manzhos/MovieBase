package com.defaultapps.moviebase.ui;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


public class TestUtils {

    public static void addFragmentToFragmentManager(Fragment fragment,
                                                    AppCompatActivity activity,
                                                    @IdRes int containerId) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    public static void removeFragmentFromFragmentManager(Fragment fragment,
                                                         AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }
}
