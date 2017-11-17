package com.defaultapps.moviebase.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.defaultapps.moviebase.R;


public class TestUtils {

    public static void addFragmentToFragmentManager(Fragment fragment,
                                                    AppCompatActivity activity) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrame, fragment)
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
