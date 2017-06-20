package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.data.firebase.FirebaseService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class BookmarksUseCaseImpl implements BookmarksUseCase {

    private FirebaseService firebaseService;

    @Inject
    public BookmarksUseCaseImpl(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    public Observable<Boolean> removeItemFromDatabase(String key) {
        return firebaseService.removeFromFavorites(key);
    }
}
