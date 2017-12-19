package com.defaultapps.moviebase.utils.analytics;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AnalyticsImpl implements Analytics {

    private final FirebaseAnalytics firebaseAnalytics;

    @Inject
    AnalyticsImpl(FirebaseAnalytics firebaseAnalytics) {
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void sendScreenSelect(String screenName) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "screen");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, screenName);

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
