package com.defaultapps.moviebase.data.firebase;

import android.support.annotation.NonNull;

import com.defaultapps.moviebase.data.local.Cache;
import com.defaultapps.moviebase.utils.ResponseOrError;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import timber.log.Timber;

@Singleton
public class FavoritesManager {

    private FirebaseService firebaseService;
    private Cache<Integer, BehaviorSubject<Boolean>> favoritesCache;

    @Inject
    FavoritesManager(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
        favoritesCache = new Cache<>(postId -> BehaviorSubject.createDefault(false));
    }

    private void updateFavoritesCache(final @NonNull List<Integer> favs) {
        if (favs.isEmpty()) {
            favoritesCache.invalidate();
        }
        for (Integer favId : favs) {
            updateFavorite(favId, true);
        }
    }

    private void updateFavorite(final @NonNull Integer id, final boolean favorite) {
        favoritesCache.get(id).onNext(favorite);
    }

    public Observable<ResponseOrError<Boolean>> addOrRemoveFromFavs(final int movieId, final String posterPath) {
        return getIsFavoriteObservable(movieId)
                .take(1)
                .doOnNext(isCurrentlyFavorite -> {
                    updateFavorite(movieId, !isCurrentlyFavorite); // toggle value before request for better User Experience
                })
                .switchMap(isCurrentlyFavorite ->  (isCurrentlyFavorite ? firebaseService.removeFromFavorites(movieId) : firebaseService.addToFavorites(movieId, posterPath))
                        .compose(ResponseOrError.toResponseOrErrorObservable()) // it's changing error event to onNext event to not finish a chain
                        .doOnNext(responseOrError -> {
                            if (responseOrError.isError()) {
                                updateFavorite(movieId, isCurrentlyFavorite); // set back to previous value because of error
                            }
                        }));
    }

    public void fetchAllFavs() {
        firebaseService.fetchAllFavorites()
                .subscribe(this::updateFavoritesCache, Timber::e);
    }

    public Observable<Boolean> getIsFavoriteObservable(final @NonNull Integer id) {
        return favoritesCache.get(id);
    }

}
