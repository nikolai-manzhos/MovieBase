package com.defaultapps.moviebase.ui.discover;

import com.defaultapps.moviebase.data.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerActivity
public class DiscoverPresenterImpl extends BasePresenter<DiscoverContract.DiscoverView> implements DiscoverContract.DiscoverPresenter {

    private DiscoverUseCase discoverUseCase;

    @Inject
    public DiscoverPresenterImpl(DiscoverUseCase discoverUseCase) {
        this.discoverUseCase = discoverUseCase;
    }

    @Override
    public void requestData() {
        getCompositeDisposable().add(
                discoverUseCase.provideGenresList()
                .subscribe(
                        genres -> {
                            if (getView() != null) getView().showData(genres);
                        },
                        err -> {}
                )
        );
    }
}
