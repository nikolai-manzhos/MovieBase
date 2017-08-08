package com.defaultapps.moviebase.ui.search;


import com.defaultapps.moviebase.data.usecase.SearchUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerFragment
public class SearchPresenterImpl extends BasePresenter<SearchContract.SearchView> implements SearchContract.SearchPresenter {

    private SearchUseCase searchUseCase;

    @Inject
    public SearchPresenterImpl(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @Override
    public void requestSearchResults(String query, boolean force) {
        if (getView() != null) {
            getView().hideData();
            getView().hideError();
            getView().hideEmpty();
            getView().showLoading();
        }
        getCompositeDisposable().add(
                searchUseCase.requestSearchResults(query, force).subscribe(
                        moviesResponse -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideError();
                                getView().showData();
                                if (moviesResponse.getTotalResults() == 0) getView().showEmpty();
                                getView().displaySearchResults(moviesResponse);
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().hideData();
                                getView().hideEmpty();
                                getView().showError();
                            }
                        }
                )
        );
    }
}
