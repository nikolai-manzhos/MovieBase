package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.di.module.ActivityModule;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;

import dagger.Component;

/**
 * Created on 5/14/2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(HomeViewImpl homeView);
}
