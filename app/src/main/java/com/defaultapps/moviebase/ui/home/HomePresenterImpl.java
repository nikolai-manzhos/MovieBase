package com.defaultapps.moviebase.ui.home;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.usecase.HomeUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.rx.RxBus;

import java.util.List;

import javax.inject.Inject;

@PerFragment
public class HomePresenterImpl extends BasePresenter<HomeContract.HomeView>
        implements HomeContract.HomePresenter {

    private final HomeUseCase homeUseCase;
    private final RxBus rxBus;

    @Inject
    HomePresenterImpl(HomeUseCase homeUseCase, RxBus rxBus) {
        this.homeUseCase = homeUseCase;
        this.rxBus = rxBus;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(HomeContract.HomeView view) {
        super.onAttach(view);
        rxBus.subscribe(AppConstants.HOME_INSTANT_CACHE,
                this,
                response -> getView().receiveResults((List<MoviesResponse>) response));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        rxBus.unsubscribe(this);
    }

    @Override
    public void requestMoviesData(boolean force) {
        getView().showLoading();
        getCompositeDisposable().add(
            homeUseCase.requestHomeData(force)
                .subscribe(
                        moviesResponses -> {
                            getView().hideLoading();
                            getView().receiveResults(moviesResponses);
                        },
                        err -> {
                            getView().hideLoading();
                            getView().displayErrorMessage();
                        },
                        () -> {
                            if (getView().isRefreshing()) {
                                getView().hideLoading();
                            }
                        }
                )
        );
    }

    @Override
    public void openProfileScreen() {
        getView().displayProfileScreen();
    }
}
