package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.data.firebase.LoggedUser;
import com.defaultapps.moviebase.data.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.data.usecase.GenreUseCase;
import com.defaultapps.moviebase.data.usecase.HomeUseCase;
import com.defaultapps.moviebase.data.usecase.MovieUseCase;
import com.defaultapps.moviebase.data.usecase.PersonUseCase;
import com.defaultapps.moviebase.data.usecase.SearchUseCase;
import com.defaultapps.moviebase.di.module.ApplicationModule;
import com.defaultapps.moviebase.di.module.SchedulersModule;
import com.defaultapps.moviebase.di.module.UseCaseModule;
import com.defaultapps.moviebase.utils.RxBus;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, UseCaseModule.class, SchedulersModule.class})
public interface ApplicationComponent {
    HomeUseCase homeUseCaseImpl();
    DiscoverUseCase discoverUseCaseImpl();
    GenreUseCase genreUseCaseImpl();
    MovieUseCase movieUseCaseImpl();
    SearchUseCase searchUseCaseImpl();
    PersonUseCase personUseCaseImpl();
    DatabaseReference databaseReference();
    LoggedUser loggedUser();
    RxBus rxBus();
}
