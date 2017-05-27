package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.MvpPresenter;

import java.util.List;


public interface HomeContract {

    interface HomePresenter extends MvpPresenter<HomeView> {
        void requestMoviesData(boolean force);
    }

    interface HomeView extends MvpView {
        void receiveResults(List<MoviesResponse> results);
    }
}
