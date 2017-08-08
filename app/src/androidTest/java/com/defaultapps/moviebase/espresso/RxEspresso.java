package com.defaultapps.moviebase.espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;

/**
 * Created on 7/30/2017.
 */

public final class RxEspresso {
    private static final String TAG = "RxEspresso";
    private static int LOG_LEVEL = LogLevel.NONE;
    private static RxEspresso INSTANCE;

    private CountingIdlingResource countingIdlingResource;

    public static void setLogLevel(@LogLevel int logLevel) {
        RxEspresso.LOG_LEVEL = logLevel;
    }

    public static void increment() {
        get().countingIdlingResource.increment();
    }

    public static void decrement() {
        get().countingIdlingResource.decrement();
    }

    public static boolean isIdleNow() {
        return get().countingIdlingResource.isIdleNow();
    }

    private RxEspresso() {

        boolean debug = false;
        if (LOG_LEVEL == LogLevel.DEBUG) {
            debug = true;
        }
        countingIdlingResource = new CountingIdlingResource(TAG, debug);
        Espresso.registerIdlingResources(countingIdlingResource);
    }

    private static RxEspresso get() {
        if (INSTANCE == null) {
            INSTANCE = new RxEspresso();
        }
        return INSTANCE;
    }

}