package com.defaultapps.moviebase.di.module;

import com.defaultapps.moviebase.data.AppSchedulerProvider;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.utils.rx.MainThreadScheduler;
import com.defaultapps.moviebase.utils.rx.ThreadScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulersModule {

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Singleton
    @Provides
    ThreadScheduler provideThreadScheduler() {
        return new MainThreadScheduler();
    }
}
