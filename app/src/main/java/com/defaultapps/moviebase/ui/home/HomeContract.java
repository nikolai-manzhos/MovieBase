package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BasePresenter;
import com.defaultapps.moviebase.ui.base.BaseView;
import com.defaultapps.moviebase.ui.base.Presenter;

import java.util.List;


public interface HomeContract {

    interface HomePresenter extends Presenter<HomeView> {
        void requestMoviesData(boolean force);
    }

    interface HomeView extends BaseView {
        void receiveResults(List<MoviesResponse> results);
    }
}
