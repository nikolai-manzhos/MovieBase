package com.defaultapps.moviebase.ui.home;

import android.util.Log;

import com.defaultapps.moviebase.data.repository.HomeRepositoryImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on 5/14/2017.
 */
@PerActivity
public class HomePresenterImpl extends BasePresenter<HomeView> implements HomePresenter {

    private HomeRepositoryImpl homeRepository;

    @Inject
    public HomePresenterImpl(CompositeDisposable compositeDisposable,
                             HomeRepositoryImpl homeRepository) {
        super(compositeDisposable);
        this.homeRepository = homeRepository;
    }

    @Override
    public void requestMoviesData() {
        if (getView() != null) {
            getView().showLoading();
        }
        getCompositeDisposable().add(
            homeRepository.requestHomeData()
                .subscribe(
                        moviesResponses -> {
                            getView().hideLoading();
                            getView().receiveResults(moviesResponses);
                            Log.d("PRESENTER", String.valueOf(moviesResponses.get(0).getResults().size()));
                        },
                        err -> {
                            getView().hideLoading();
                            Log.d("PRESENTER", err.toString());
                        }
                )
        );
    }
}
