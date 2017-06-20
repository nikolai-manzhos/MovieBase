package com.defaultapps.moviebase.ui.search;


import com.defaultapps.moviebase.data.usecase.SearchUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerActivity
public class SearchPresenterImpl extends BasePresenter<SearchContract.SearchView> implements SearchContract.SearchPresenter {

    private SearchUseCaseImpl searchUseCase;

    @Inject
    public SearchPresenterImpl(SearchUseCaseImpl searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @Override
    public void requestSearchResults(String query, boolean force) {
        if (getView() != null) {
            getView().hideData();
            getView().hideError();
            getView().showLoading();
        }
        getCompositeDisposable().add(
                searchUseCase.requestSearchResults(query, force).subscribe(
                        moviesResponse -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideError();
                                getView().showData();
                                getView().displaySearchResults(moviesResponse);
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
}
