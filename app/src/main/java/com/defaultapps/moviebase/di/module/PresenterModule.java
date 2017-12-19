package com.defaultapps.moviebase.di.module;

import com.defaultapps.moviebase.ui.bookmarks.BookmarksContract.BookmarksPresenter;
import com.defaultapps.moviebase.ui.bookmarks.BookmarksPresenterImpl;
import com.defaultapps.moviebase.ui.discover.DiscoverContract.DiscoverPresenter;
import com.defaultapps.moviebase.ui.discover.DiscoverPresenterImpl;
import com.defaultapps.moviebase.ui.genre.GenreContract.GenrePresenter;
import com.defaultapps.moviebase.ui.genre.GenrePresenterImpl;
import com.defaultapps.moviebase.ui.home.HomeContract.HomePresenter;
import com.defaultapps.moviebase.ui.home.HomePresenterImpl;
import com.defaultapps.moviebase.ui.main.MainContract.MainPresenter;
import com.defaultapps.moviebase.ui.main.MainPresenterImpl;
import com.defaultapps.moviebase.ui.movie.MovieContract.MoviePresenter;
import com.defaultapps.moviebase.ui.movie.MoviePresenterImpl;
import com.defaultapps.moviebase.ui.person.PersonContract.PersonPresenter;
import com.defaultapps.moviebase.ui.person.PersonPresenterImpl;
import com.defaultapps.moviebase.ui.search.SearchContract.SearchPresenter;
import com.defaultapps.moviebase.ui.search.SearchPresenterImpl;
import com.defaultapps.moviebase.ui.user.UserContract.UserPresenter;
import com.defaultapps.moviebase.ui.user.UserPresenterImpl;

import dagger.Binds;
import dagger.Module;

@Module
@SuppressWarnings("unused")
public abstract class PresenterModule {

    @Binds
    abstract BookmarksPresenter bindBookmarksPresenter(BookmarksPresenterImpl bookmarksPresenter);

    @Binds
    abstract DiscoverPresenter bindDiscoverPresenter(DiscoverPresenterImpl discoverPresenter);

    @Binds
    abstract GenrePresenter bindGenrePresenter(GenrePresenterImpl genrePresenter);

    @Binds
    abstract HomePresenter bindHomePresenter(HomePresenterImpl homePresenter);

    @Binds
    abstract MoviePresenter bindMoviePresenter(MoviePresenterImpl moviePresenter);

    @Binds
    abstract PersonPresenter bindPersonPresenter(PersonPresenterImpl personPresenter);

    @Binds
    abstract SearchPresenter bindSearchPresenter(SearchPresenterImpl searchPresenter);

    @Binds
    abstract UserPresenter bindUserPresenter(UserPresenterImpl userPresenter);

    @Binds
    abstract MainPresenter bindMainPresenter(MainPresenterImpl mainPresenter);
}
