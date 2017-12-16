package com.defaultapps.moviebase.di.module;

import android.content.Context;

import com.defaultapps.moviebase.di.ApplicationContext;
import com.defaultapps.moviebase.utils.analytics.Analytics;
import com.defaultapps.moviebase.utils.analytics.AnalyticsImpl;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AnalyticsModule {

    @Singleton
    @Provides
    FirebaseAnalytics provideFirebaseAnalytics(@ApplicationContext Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    Analytics provideAnalytics(AnalyticsImpl analytics) {
        return analytics;
    }

}
