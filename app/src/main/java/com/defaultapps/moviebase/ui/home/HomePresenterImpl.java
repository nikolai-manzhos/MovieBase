package com.defaultapps.moviebase.ui.home;

import android.util.Log;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.usecase.HomeUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

@PerFragment
public class HomePresenterImpl extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresenter {

    private HomeUseCase homeUseCase;
    private RxBus rxBus;

    @Inject
    public HomePresenterImpl(HomeUseCase homeUseCase, RxBus rxBus) {
        this.homeUseCase = homeUseCase;
        this.rxBus = rxBus;
    }

    @Override
    public void onAttach(HomeContract.HomeView view) {
        super.onAttach(view);
        rxBus.subscribe(AppConstants.HOME_INSTANT_CACHE,
                this,
                response -> {
                    //noinspection unchecked
                    getView().receiveResults((List<MoviesResponse>) response);
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        rxBus.unsubscribe(this);
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
                                //TODO: Show error message
                            }
                        }
                )
        );
    }
}
