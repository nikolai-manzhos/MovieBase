package com.defaultapps.moviebase;

import timber.log.Timber;

/**
 * Release application.
 */
public class ReleaseApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new CrashlyticsTree());
    }
}
