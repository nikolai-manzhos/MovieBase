package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseView;

import java.util.List;

/**
 * Created on 5/14/2017.
 */

public interface HomeView extends BaseView {
    void receiveResults(List<MoviesResponse> results);
}
