package com.defaultapps.moviebase.ui.search;

import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


@PerActivity
public class SearchPresenter extends BasePresenter<SearchContract.SearchView> implements SearchContract.SearchPresenter {

    @Inject
    public SearchPresenter(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }


}
