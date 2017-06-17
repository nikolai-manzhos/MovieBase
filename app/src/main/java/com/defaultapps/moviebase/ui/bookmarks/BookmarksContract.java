package com.defaultapps.moviebase.ui.bookmarks;

import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.MvpPresenter;


public interface BookmarksContract {

    interface BookmarksPresenter extends MvpPresenter<BookmarksView> {
        void removeItemFromFavorites(String key);
    }

    interface BookmarksView extends MvpView {
        void displayErrorMessage();
    }
}
