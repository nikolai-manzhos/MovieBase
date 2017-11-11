package com.defaultapps.moviebase.ui.bookmarks;

import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.MvpView;

public interface BookmarksContract {

    interface BookmarksPresenter extends MvpPresenter<BookmarksView> {
        void displayNoUserView();
    }

    interface BookmarksView extends MvpView {
        void displayErrorMessage();
        void showNoUserMessage();
        void hideNoUserMessage();
    }
}
