package com.defaultapps.moviebase.di.module;

import android.app.Activity;
import android.content.Context;

import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityContext
    @PerActivity
    @Provides
    Context provideActivityContext() {
        return activity;
    }

    /**
     * Provide DefaultNavigator which is tied to Activity lifecycle
     * @return instance of {#com.}
     */
    @ActivityContext
    @PerActivity
    @Provides
    DefaultNavigator provideDefaultNavigator() {
        return new DefaultNavigator<>();
    }
}
