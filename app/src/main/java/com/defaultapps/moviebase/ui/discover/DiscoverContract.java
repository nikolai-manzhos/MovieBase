package com.defaultapps.moviebase.ui.discover;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;


public interface DiscoverContract {

    interface DiscoverPresenter extends MvpPresenter<DiscoverView> {
        void requestData();
    }

    interface DiscoverView extends MvpView {
        void showData(Genres genres);
    }
}
