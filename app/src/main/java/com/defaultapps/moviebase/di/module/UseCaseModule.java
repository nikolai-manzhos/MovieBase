package com.defaultapps.moviebase.di.module;

import com.defaultapps.moviebase.domain.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.domain.usecase.DiscoverUseCaseImpl;
import com.defaultapps.moviebase.domain.usecase.GenreUseCase;
import com.defaultapps.moviebase.domain.usecase.GenreUseCaseImpl;
import com.defaultapps.moviebase.domain.usecase.HomeUseCase;
import com.defaultapps.moviebase.domain.usecase.HomeUseCaseImpl;
import com.defaultapps.moviebase.domain.usecase.MovieUseCase;
import com.defaultapps.moviebase.domain.usecase.MovieUseCaseImpl;
import com.defaultapps.moviebase.domain.usecase.PersonUseCase;
import com.defaultapps.moviebase.domain.usecase.PersonUseCaseImpl;
import com.defaultapps.moviebase.domain.usecase.SearchUseCase;
import com.defaultapps.moviebase.domain.usecase.SearchUseCaseImpl;

import dagger.Binds;
import dagger.Module;

@Module
@SuppressWarnings("unused")
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
