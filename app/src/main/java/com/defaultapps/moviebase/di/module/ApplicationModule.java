package com.defaultapps.moviebase.di.module;

import android.app.Application;
import android.content.Context;

import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.di.ApplicationContext;
import com.defaultapps.moviebase.di.scope.PerActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @ApplicationContext
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.DEFAULT;
    }
}
