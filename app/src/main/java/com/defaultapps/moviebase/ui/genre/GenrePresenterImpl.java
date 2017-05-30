package com.defaultapps.moviebase.ui.genre;


import com.defaultapps.moviebase.data.interactor.GenreUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class GenrePresenterImpl extends BasePresenter<GenreContract.GenreView> implements GenreContract.GenrePresenter {

    private GenreUseCaseImpl genreUseCase;

    @Inject
    GenrePresenterImpl(GenreUseCaseImpl genreUseCase) {
        this.genreUseCase = genreUseCase;
    }

    @Override
    public void requestMovies(String genreId, boolean force) {
        if (getView() != null) {
            getView().showLoading();
            getView().hideError();
        }
        getCompositeDisposable().add(
                genreUseCase.requestHomeData(genreId, force)
                .subscribe(
                        moviesResponse -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideError();
                                getView().showMovies(moviesResponse);
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().showError();
                            }
                        }
                )
        );
    }
}
