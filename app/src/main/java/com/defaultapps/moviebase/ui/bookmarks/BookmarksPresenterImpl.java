package com.defaultapps.moviebase.ui.bookmarks;

import com.defaultapps.moviebase.data.interactor.BookmarksUseCaseImpl;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class BookmarksPresenterImpl extends BasePresenter<BookmarksContract.BookmarksView> implements BookmarksContract.BookmarksPresenter {

    private BookmarksUseCaseImpl useCase;

    @Inject
    BookmarksPresenterImpl(BookmarksUseCaseImpl useCase) {
        this.useCase = useCase;
    }

    @Override
    public void removeItemFromFavorites(String key) {
        getCompositeDisposable().add(
                useCase.removeItemFromDatabase(key)
                .subscribe(
                        success -> {},
                        err -> {
                            if (getView() != null) {
                                getView().displayErrorMessage();
                            }
                        }
                )
        );
    }
}
