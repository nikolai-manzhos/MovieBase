package com.defaultapps.moviebase.ui.discover;

import android.util.Log;

import com.defaultapps.moviebase.data.usecase.DiscoverUseCase;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerFragment
public class DiscoverPresenterImpl extends BasePresenter<DiscoverContract.DiscoverView> implements DiscoverContract.DiscoverPresenter {

    private DiscoverUseCase discoverUseCase;

    @Inject
    public DiscoverPresenterImpl(DiscoverUseCase discoverUseCase) {
        this.discoverUseCase = discoverUseCase;
        Log.d("DiscoverPresenter", this.toString());
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
