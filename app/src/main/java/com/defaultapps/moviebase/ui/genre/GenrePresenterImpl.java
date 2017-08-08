package com.defaultapps.moviebase.ui.genre;


import com.defaultapps.moviebase.data.usecase.GenreUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerFragment
public class GenrePresenterImpl extends BasePresenter<GenreContract.GenreView> implements GenreContract.GenrePresenter {

    private GenreUseCase genreUseCase;

    @Inject
    GenrePresenterImpl(GenreUseCase genreUseCase) {
        this.genreUseCase = genreUseCase;
    }

    @Override
    public void requestMovies(String genreId, boolean force) {
        if (getView() != null) {
            getView().showLoading();
            getView().hideError();
        }
        getCompositeDisposable().add(
                genreUseCase.requestGenreData(genreId, force)
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
