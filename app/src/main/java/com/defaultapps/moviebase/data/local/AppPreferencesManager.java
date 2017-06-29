package com.defaultapps.moviebase.data.local;

import com.defaultapps.preferenceshelper.DefaultPreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesManager extends DefaultPreferencesManager {

    @Inject
    AppPreferencesManager() {}

    public boolean getAdultStatus() {
        return getPreferencesHelper().getBoolean("adult", false);
    }
}
