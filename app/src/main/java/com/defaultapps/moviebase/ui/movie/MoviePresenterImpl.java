package com.defaultapps.moviebase.ui.movie;

import com.defaultapps.moviebase.data.interactor.MovieUseCase;
import com.defaultapps.moviebase.data.interactor.MovieUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class MoviePresenterImpl extends BasePresenter<MovieContract.MovieView> implements MovieContract.MoviePresenter {

    private MovieUseCase movieUseCase;

    @Inject
    MoviePresenterImpl(CompositeDisposable compositeDisposable,
                              MovieUseCaseImpl movieUseCase) {
        super(compositeDisposable);
        this.movieUseCase = movieUseCase;
    }

    @Override
    public void requestMovieInfo(Integer movieId) {
        //TODO make request
    }
}
