package com.defaultapps.moviebase.ui.discover;


import com.defaultapps.moviebase.data.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerFragment
public class DiscoverPresenterImpl extends BasePresenter<DiscoverContract.DiscoverView> implements DiscoverContract.DiscoverPresenter {

    private final DiscoverUseCase discoverUseCase;

    @Inject
    DiscoverPresenterImpl(DiscoverUseCase discoverUseCase) {
        this.discoverUseCase = discoverUseCase;
    }

    @Override
    public void requestData() {
        getCompositeDisposable().add(
                discoverUseCase.provideGenresList()
                .subscribe(
                        genres -> getView().showData(genres),
                        err -> {}
                )
        );
    }
}
