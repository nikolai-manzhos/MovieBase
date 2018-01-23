package com.defaultapps.moviebase.ui.movie;


import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.Navigator;

public interface MovieContract {

    interface MovieNavigator extends Navigator<MovieView> {
        void toFullScreenVideoActivity(String videoPath);
        void toPersonActivity(int personId);
        void shareAction(String message);
    }

    interface MoviePresenter extends MvpPresenter<MovieView> {
        void requestMovieInfo(Integer movieId, boolean force);
        void requestFavoriteStatus(int movieId);
        void addOrRemoveFromFavorites(int movieId, String posterPath);
    }

    interface MovieView extends MvpView {
        void hideData();
        void showData();
        void hideError();
        void showError();
        void displayMovieInfo(MovieDetailResponse movieInfo);
        void displayTransactionError();
        void setFabStatus(boolean isActive);
        void displayLoginScreen();
    }

}
