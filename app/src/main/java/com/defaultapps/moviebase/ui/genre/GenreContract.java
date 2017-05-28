package com.defaultapps.moviebase.ui.genre;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.MvpPresenter;


interface GenreContract {

    interface GenrePresenter extends MvpPresenter<GenreView> {
        void requestMovies(String genreId, boolean force);
    }

    interface GenreView extends MvpView {
        void showMovies(MoviesResponse movies);
        void showError();
        void hideError();
    }
}
