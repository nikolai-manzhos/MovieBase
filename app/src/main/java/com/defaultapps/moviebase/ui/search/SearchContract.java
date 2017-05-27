package com.defaultapps.moviebase.ui.search;

import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.MvpPresenter;

public class SearchContract {

    interface SearchPresenter extends MvpPresenter<SearchView> {}

    interface SearchView extends MvpView {}
}
