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

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    public HomeUseCase provideHomeUseCase(HomeUseCaseImpl homeUseCase) {
        return homeUseCase;
    }

    @Provides
    public DiscoverUseCase provideDiscoverUseCase(DiscoverUseCaseImpl discoverUseCase) {
        return discoverUseCase;
    }

    @Provides
    public GenreUseCase provideGenreUseCase(GenreUseCaseImpl genreUseCase) {
        return genreUseCase;
    }

    @Provides
    public MovieUseCase provideMovieUseCase(MovieUseCaseImpl movieUseCase) {
        return movieUseCase;
    }

    @Provides
    public SearchUseCase provideSearchUseCase(SearchUseCaseImpl searchUseCase) {
        return searchUseCase;
    }

    @Provides
    public PersonUseCase providePersonUseCase(PersonUseCaseImpl personUseCase) {
        return personUseCase;
    }
}
