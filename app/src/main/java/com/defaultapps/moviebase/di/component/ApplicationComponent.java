package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.App;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.repository.HomeRepositoryImpl;
import com.defaultapps.moviebase.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App app);

    HomeRepositoryImpl homeRepositoryImpl();
}
