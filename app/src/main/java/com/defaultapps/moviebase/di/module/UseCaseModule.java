package com.defaultapps.moviebase.di.module;

import com.defaultapps.moviebase.data.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.data.usecase.DiscoverUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.GenreUseCase;
import com.defaultapps.moviebase.data.usecase.GenreUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.HomeUseCase;
import com.defaultapps.moviebase.data.usecase.HomeUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.MovieUseCase;
import com.defaultapps.moviebase.data.usecase.MovieUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.PersonUseCase;
import com.defaultapps.moviebase.data.usecase.PersonUseCaseImpl;
import com.defaultapps.moviebase.data.usecase.SearchUseCase;
import com.defaultapps.moviebase.data.usecase.SearchUseCaseImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract HomeUseCase provideHomeUseCase(HomeUseCaseImpl homeUseCase);

    @Binds
    @Singleton
    abstract DiscoverUseCase provideDiscoverUseCase(DiscoverUseCaseImpl discoverUseCase);

    @Binds
    @Singleton
    abstract GenreUseCase provideGenreUseCase(GenreUseCaseImpl genreUseCase);

    @Binds
    @Singleton
    abstract MovieUseCase provideMovieUseCase(MovieUseCaseImpl movieUseCase);

    @Binds
    @Singleton
    abstract SearchUseCase provideSearchUseCase(SearchUseCaseImpl searchUseCase);

    @Binds
    @Singleton
    abstract PersonUseCase providePersonUseCase(PersonUseCaseImpl personUseCase);
}
