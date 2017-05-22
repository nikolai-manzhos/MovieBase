package com.defaultapps.moviebase.ui.search;

import com.defaultapps.moviebase.ui.base.BaseView;
import com.defaultapps.moviebase.ui.base.Presenter;

public class SearchContract {

    interface SearchPresenter extends Presenter<SearchView> {}

    interface SearchView extends BaseView {}
}
