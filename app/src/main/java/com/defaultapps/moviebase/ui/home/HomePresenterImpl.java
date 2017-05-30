package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.interactor.HomeUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class HomePresenterImpl extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    private HomeUseCaseImpl homeUseCase;

    @Inject
    public HomePresenterImpl(CompositeDisposable compositeDisposable,
                             HomeUseCaseImpl homeUseCase) {
        super(compositeDisposable);
        this.homeUseCase = homeUseCase;
    }

    @Override
    public void requestMoviesData(boolean force) {
        if (getView() != null) {
            getView().showLoading();
        }
        getCompositeDisposable().add(
            homeUseCase.requestHomeData(force)
                .subscribe(
                        moviesResponses -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().receiveResults(moviesResponses);
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideLoading();
                            }
                        }
                )
        );
    }
}