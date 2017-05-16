package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.ui.base.Presenter;

/**
 * Created on 5/14/2017.
 */

public interface HomePresenter extends Presenter<HomeView> {
    void requestMoviesData();
}
