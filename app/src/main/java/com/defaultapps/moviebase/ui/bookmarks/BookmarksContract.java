package com.defaultapps.moviebase.ui.bookmarks;

import com.defaultapps.moviebase.ui.base.MvpView;
import com.defaultapps.moviebase.ui.base.MvpPresenter;


public interface BookmarksContract {

    interface BookmarksPresenter extends MvpPresenter<MvpView> {}

    interface BookmarksView extends MvpView {}
}
