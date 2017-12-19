package com.defaultapps.moviebase.ui.movie;

import com.defaultapps.moviebase.data.usecase.MovieUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import timber.log.Timber;

@PerFragment
public class MoviePresenterImpl extends BasePresenter<MovieContract.MovieView> implements MovieContract.MoviePresenter {

    private final MovieUseCase movieUseCase;

    @Inject
    MoviePresenterImpl(MovieUseCase movieUseCase) {
        super(movieUseCase);
        this.movieUseCase = movieUseCase;
    }

    @Override
    public void requestMovieInfo(Integer movieId, boolean force) {
        getView().hideError();
        getView().hideData();
        getView().showLoading();
        getCompositeDisposable().add(movieUseCase.requestMovieData(movieId, force)
                .subscribe(
                        movieInfoResponse -> {
                            getView().hideError();
                            getView().hideLoading();
                            getView().displayMovieInfo(movieInfoResponse);
                            getView().showData();
                        },
                        err -> {
                            Timber.d(err);
                            getView().hideLoading();
                            getView().hideData();
                            getView().showError();
                        }
                )
        );
    }

    @Override
    public void requestFavoriteStatus(int movieId) {
        getCompositeDisposable().add(
                movieUseCase.getCurrentState(movieId).subscribe(
                        isFavorite -> getView().setFabStatus(isFavorite)
                )
        );
    }

    @Override
    public void addOrRemoveFromFavorites(int movieId, String posterPath) {
        if (movieUseCase.getUserState()) {
            getCompositeDisposable().add(
                    movieUseCase.addOrRemoveFromDatabase(movieId, posterPath).subscribe(
                            responseOrError -> {
                                if (responseOrError.isError()) {
                                    getView().displayTransactionError();
                                }
                            }
                    )
            );
        } else {
            getView().displayLoginScreen();
        }

    }
}
