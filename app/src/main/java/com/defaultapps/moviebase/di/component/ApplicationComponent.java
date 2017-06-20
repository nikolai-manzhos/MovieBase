package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.data.firebase.LoggedUser;
import com.defaultapps.moviebase.data.usecase.BookmarksUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.DiscoverUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.GenreUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.HomeUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.MovieUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.SearchUseCaseImpl;
import com.defaultapps.moviebase.di.module.ApplicationModule;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    HomeUseCaseImpl homeUseCaseImpl();
    DiscoverUseCaseImpl discoverUseCaseImpl();
    BookmarksUseCaseImpl bookmarksUseCaseImpl();
    GenreUseCaseImpl genreUseCaseImpl();
    MovieUseCaseImpl movieUseCaseImpl();
    SearchUseCaseImpl searchUseCaseImpl();
    DatabaseReference databaseReference();
    LoggedUser loggedUser();
}
