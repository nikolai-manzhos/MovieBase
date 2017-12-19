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

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UseCaseModule {

    @Binds
    abstract HomeUseCase bindHomeUseCase(HomeUseCaseImpl homeUseCase);

    @Binds
    abstract DiscoverUseCase bindDiscoverUseCase(DiscoverUseCaseImpl discoverUseCase);

    @Binds
    abstract GenreUseCase bindGenreUseCase(GenreUseCaseImpl genreUseCase);

    @Binds
    abstract MovieUseCase bindMovieUseCase(MovieUseCaseImpl movieUseCase);

    @Binds
    abstract SearchUseCase bindSearchUseCase(SearchUseCaseImpl searchUseCase);

    @Binds
    abstract PersonUseCase bindPersonUseCase(PersonUseCaseImpl personUseCase);
}
