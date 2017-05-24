package com.defaultapps.moviebase.ui.discover;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.ui.base.BaseView;
import com.defaultapps.moviebase.ui.base.Presenter;


public interface DiscoverContract {

    interface DiscoverPresenter extends Presenter<DiscoverView> {
        void requestData();
    }

    interface DiscoverView extends BaseView {
        void showData(Genres genres);
    }
}
