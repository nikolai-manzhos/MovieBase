package com.defaultapps.moviebase.ui.search;

import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.MvpPresenter;

public interface SearchContract {

    interface SearchPresenter extends MvpPresenter<SearchView> {}

    interface SearchView extends MvpView {}
}
