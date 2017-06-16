package com.defaultapps.moviebase.ui.movie;


import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

public interface MovieContract {

    interface MoviePresenter extends MvpPresenter<MovieView> {
        void requestMovieInfo(Integer movieId, boolean force);
        void addMovieToFavorites(int movieId, String posterPath);
    }

    interface MovieView extends MvpView {
        void hideData();
        void showData();
        void hideError();
        void showError();
        void displayMovieInfo(MovieInfoResponse movieInfo);
        void displayTransactionStatus(boolean status);
    }

}
