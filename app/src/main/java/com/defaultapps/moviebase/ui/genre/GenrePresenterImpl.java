package com.defaultapps.moviebase.ui.genre;


import com.defaultapps.moviebase.data.usecase.GenreUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import timber.log.Timber;


@PerFragment
public class GenrePresenterImpl extends BasePresenter<GenreContract.GenreView> implements GenreContract.GenrePresenter {

    private final GenreUseCase genreUseCase;

    @Inject
    GenrePresenterImpl(GenreUseCase genreUseCase) {
        this.genreUseCase = genreUseCase;
    }

    @Override
    public void requestMovies(String genreId, boolean force) {
        getView().showLoading();
        getView().hideError();
        getCompositeDisposable().add(
                genreUseCase.requestGenreData(genreId, force)
                .subscribe(
                        moviesResponse -> {
                            getView().hideLoading();
                            getView().hideError();
                            getView().showMovies(moviesResponse);
                        },
                        err -> {
                            Timber.d(err);
                            getView().hideLoading();
                            getView().showError();
                        }
                )
        );
    }

    @Override
    public void requestMoreMovies(String genreId) {
        getCompositeDisposable().add(
                genreUseCase.requestMoreGenreData(genreId)
                .subscribe(
                        moviesResponse -> getView().showMoreMovies(moviesResponse),
                        err -> getView().showLoadMoreError()
                )
        );
    }
}
