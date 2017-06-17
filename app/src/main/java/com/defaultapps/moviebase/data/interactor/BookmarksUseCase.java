package com.defaultapps.moviebase.data.interactor;

import io.reactivex.Observable;

public interface BookmarksUseCase {
    Observable<Boolean> removeItemFromDatabase(String key);
}
