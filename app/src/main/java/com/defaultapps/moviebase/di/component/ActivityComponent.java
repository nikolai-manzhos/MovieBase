package com.defaultapps.moviebase.di.component;

import android.content.Context;

import com.defaultapps.moviebase.data.firebase.LoggedUser;
import com.defaultapps.moviebase.data.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.data.usecase.GenreUseCase;
import com.defaultapps.moviebase.data.usecase.HomeUseCase;
import com.defaultapps.moviebase.data.usecase.MovieUseCase;
import com.defaultapps.moviebase.data.usecase.PersonUseCase;
import com.defaultapps.moviebase.data.usecase.SearchUseCase;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.module.ActivityModule;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.utils.RxBus;
import com.google.firebase.database.DatabaseReference;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    HomeUseCase homeUseCaseImpl();
    DiscoverUseCase discoverUseCaseImpl();
    GenreUseCase genreUseCaseImpl();
    MovieUseCase movieUseCaseImpl();
    SearchUseCase searchUseCaseImpl();
    PersonUseCase personUseCaseImpl();
    DatabaseReference databaseReference();
    LoggedUser loggedUser();
    RxBus rxBus();
    @ActivityContext Context context();

    void inject(MainActivity mainActivity);
}
