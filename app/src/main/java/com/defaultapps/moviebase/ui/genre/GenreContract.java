package com.defaultapps.moviebase.ui.genre;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;


interface GenreContract {

    interface GenrePresenter extends MvpPresenter<GenreView> {
        void requestMovies(String genreId, boolean force);
        void requestMoreMovies(String genreId);
    }

    interface GenreView extends MvpView {
        void showMovies(MoviesResponse movies);
        void showMoreMovies(MoviesResponse movies);
        void showLoadMoreError();
        void showError();
        void hideError();
    }
}
