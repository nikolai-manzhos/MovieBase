package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.data.interactor.DiscoverUseCaseImpl;
import com.defaultapps.moviebase.data.interactor.GenreUseCaseImpl;
import com.defaultapps.moviebase.data.interactor.HomeUseCaseImpl;
import com.defaultapps.moviebase.data.interactor.MovieUseCaseImpl;
import com.defaultapps.moviebase.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    HomeUseCaseImpl homeUseCaseImpl();
    DiscoverUseCaseImpl discoverUseCaseImpl();
    GenreUseCaseImpl genreUseCaseImpl();
    MovieUseCaseImpl movieUseCaseImpl();
}