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
    MoviePresenterImpl(MovieUseCaseImpl movieUseCase) {
        this.movieUseCase = movieUseCase;
    }

    @Override
    public void requestMovieInfo(Integer movieId) {
        if (getView() != null) {
            getView().hideError();
            getView().hideData();
            getView().showLoading();
        }
        movieUseCase.requestMovieData(movieId)
                .subscribe(
                        movieInfoResponse -> {
                            if (getView() != null) {
                                getView().hideError();
                                getView().hideLoading();
                                getView().showMovieInfo(movieInfoResponse);
                                getView().showData();
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideData();
                                getView().showError();
                            }
                        }
                );
    }
}
