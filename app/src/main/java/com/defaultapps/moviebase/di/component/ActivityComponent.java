package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.di.module.ActivityModule;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.discover.DiscoverViewImpl;
import com.defaultapps.moviebase.ui.genre.GenrePresenterImpl;
import com.defaultapps.moviebase.ui.genre.GenreViewImpl;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(HomeViewImpl homeView);
    void inject(DiscoverViewImpl discoverView);
    void inject(GenreViewImpl genreView);
}
