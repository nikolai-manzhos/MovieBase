package com.defaultapps.moviebase;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.utils.analytics.Analytics;

import static org.mockito.Mockito.mock;


public class TestUtils {

    public static void addFragmentToFragmentManager(BaseFragment fragment,
                                                    AppCompatActivity activity) {
        fragment.analytics = mock(Analytics.class);
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
