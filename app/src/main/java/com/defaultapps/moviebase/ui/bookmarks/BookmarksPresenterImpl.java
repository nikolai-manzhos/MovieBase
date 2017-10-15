package com.defaultapps.moviebase.ui.bookmarks;

import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.base.BasePresenter;

import javax.inject.Inject;


@PerFragment
public class BookmarksPresenterImpl extends BasePresenter<BookmarksContract.BookmarksView>
        implements BookmarksContract.BookmarksPresenter {

    @Inject
    BookmarksPresenterImpl() {}

    @Override
    public void displayNoUserView() {
        getView().showNoUserMessage();
    }
}
