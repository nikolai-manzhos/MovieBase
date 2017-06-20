package com.defaultapps.moviebase.data.usecase;

import io.reactivex.Observable;

public interface BookmarksUseCase {
    Observable<Boolean> removeItemFromDatabase(String key);
}
