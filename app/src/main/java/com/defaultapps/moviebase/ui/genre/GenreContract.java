package com.defaultapps.moviebase.ui.genre;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseView;
import com.defaultapps.moviebase.ui.base.Presenter;


public class GenreContract {

    interface GenrePresenter extends Presenter<GenreView> {
        void requestMovies(String genreId, boolean force);
    }

    interface GenreView extends BaseView {
        void showMovies(MoviesResponse movies);
    }
}
