package com.defaultapps.moviebase.ui.movie;

import com.defaultapps.moviebase.data.usecase.MovieUseCase;
import com.defaultapps.moviebase.data.usecase.MovieUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class MoviePresenterImpl extends BasePresenter<MovieContract.MovieView> implements MovieContract.MoviePresenter {

    private MovieUseCase movieUseCase;

    @Inject
    MoviePresenterImpl(MovieUseCaseImpl movieUseCase) {
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
    public void addMovieToFavorites(int movieId, String posterPath) {
        getCompositeDisposable().add(
                movieUseCase.addMovieToDatabase(movieId, posterPath).subscribe(
                    success -> {
                        if (getView() != null) {
                            getView().displayTransactionStatus(true);
                        }
                    },
                    err -> {
                        if (getView() != null) {
                            getView().displayTransactionStatus(false);
                        }
                    }
                )
        );
    }
}
