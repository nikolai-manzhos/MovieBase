package com.defaultapps.moviebase.ui.movie;

import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class MoviePresenterImpl extends BasePresenter<MovieContract.MovieView> implements MovieContract.MoviePresenter {

    @Inject
    public MoviePresenterImpl(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }
}
