package com.defaultapps.moviebase.ui.movie;

import com.defaultapps.moviebase.data.usecase.MovieUseCase;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerFragment
public class MoviePresenterImpl extends BasePresenter<MovieContract.MovieView> implements MovieContract.MoviePresenter {

    private MovieUseCase movieUseCase;

    @Inject
    MoviePresenterImpl(MovieUseCase movieUseCase) {
        this.movieUseCase = movieUseCase;
    }

    @Override
    public void requestMovieInfo(Integer movieId, boolean force) {
        if (getView() != null) {
            getView().hideError();
            getView().hideData();
            getView().showLoading();
        }
        getCompositeDisposable().add(movieUseCase.requestMovieData(movieId, force)
                .subscribe(
                        movieInfoResponse -> {
                            if (getView() != null) {
                                getView().hideError();
                                getView().hideLoading();
                                getView().displayMovieInfo(movieInfoResponse);
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
                )
        );
    }

    @Override
    public void requestFavoriteStatus(int movieId) {
        getCompositeDisposable().add(
                movieUseCase.getCurrentState(movieId).subscribe(
                        isFavorite -> {
                            if (getView() != null) {
                                getView().setFabStatus(isFavorite);
                            }
                        }
                )
        );
    }

    @Override
    public void addOrRemoveFromFavorites(int movieId, String posterPath) {
        getCompositeDisposable().add(
                movieUseCase.addOrRemoveFromDatabase(movieId, posterPath).subscribe(
                    responseOrError -> {
                        if (getView() != null) {
                            if (responseOrError.isError()) {
                                getView().displayTransactionError();
                            }
                        }
                    }
                )
        );
    }
}
