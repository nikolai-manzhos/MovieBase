package com.defaultapps.moviebase.di.module;

import android.app.Application;

import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.di.scope.PerActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 5/14/2017.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.DEFAULT;
    }
}
