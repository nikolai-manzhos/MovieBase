package com.defaultapps.moviebase.ui.search;


import com.defaultapps.moviebase.data.usecase.SearchUseCase;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerFragment
public class SearchPresenterImpl extends BasePresenter<SearchContract.SearchView> implements SearchContract.SearchPresenter {

    private final SearchUseCase searchUseCase;

    @Inject
    SearchPresenterImpl(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    @Override
    public void requestSearchResults(String query, boolean force) {
        getView().hideData();
        getView().hideError();
        getView().hideEmpty();
        getView().showLoading();
        getCompositeDisposable().add(
                searchUseCase.requestSearchResults(query, force).subscribe(
                        moviesResponse -> {
                            getView().hideLoading();
                            getView().hideError();
                            getView().showData();
                            if (moviesResponse.getTotalResults() == 0) getView().showEmpty();
                            getView().displaySearchResults(moviesResponse);
                        },
                        err -> {
                            getView().hideLoading();
                            getView().hideData();
                            getView().hideEmpty();
                            getView().showError();
                        }
                )
        );
    }

    @Override
    public void requestMoreSearchResults(String query) {
        getCompositeDisposable().add(
                searchUseCase.requestMoreSearchResults(query)
                .subscribe(
                        moviesResponse -> getView().displayMoreSearchResults(moviesResponse),
                        err -> getView().showLoadMoreError()
                )
        );
    }

    @Override
    public void onSearchViewClose() {
        getView().showSearchStart();
        getView().hideLoading();
        getView().hideError();
        getView().hideData();
        getView().hideEmpty();
    }

    @Override
    public void onSearchViewOpen() {
        getView().hideSearchStart();
    }
}
