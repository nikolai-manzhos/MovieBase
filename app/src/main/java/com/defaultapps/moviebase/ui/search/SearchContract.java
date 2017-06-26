package com.defaultapps.moviebase.ui.search;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

public interface SearchContract {

    interface SearchPresenter extends MvpPresenter<SearchView> {
        void requestSearchResults(String query, boolean force);
    }

    interface SearchView extends MvpView {
        void hideData();
        void showData();
        void hideError();
        void showError();
        void hideEmpty();
        void showEmpty();
        void displaySearchResults(MoviesResponse moviesResponse);
    }
}
