package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.interactor.HomeUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@PerActivity
public class HomePresenterImpl extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    private HomeUseCaseImpl homeRepository;

    @Inject
    public HomePresenterImpl(CompositeDisposable compositeDisposable,
                             HomeUseCaseImpl homeRepository) {
        super(compositeDisposable);
        this.homeRepository = homeRepository;
    }

    @Override
    public void requestMoviesData(boolean force) {
        if (getView() != null) {
            getView().showLoading();
        }
        getCompositeDisposable().add(
            homeRepository.requestHomeData(force)
                .subscribe(
                        moviesResponses -> {
                            getView().hideLoading();
                            getView().receiveResults(moviesResponses);
                        },
                        err -> {
                            getView().hideLoading();
                        }
                )
        );
    }
}
