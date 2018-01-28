package com.defaultapps.moviebase.domain.usecase;


import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.domain.base.BaseUseCase;
import com.defaultapps.moviebase.domain.repository.ApiRepository;
import com.defaultapps.moviebase.utils.ResponseOrError;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class MovieUseCaseImpl extends BaseUseCase implements MovieUseCase {

    private static final int INITIAL_ID = -1;

    private final ApiRepository apiRepository;
    private final FavoritesManager favoritesManager;
    private final Provider<FirebaseUser> firebaseUserProvider;

    private Disposable movieInfoDisposable;
    private ReplaySubject<MovieDetailResponse> movieInfoReplaySubject;
    private int currentId = INITIAL_ID;

    @Inject
    MovieUseCaseImpl(ApiRepository apiRepository,
                     FavoritesManager favoritesManager,
                     Provider<FirebaseUser> firebaseUserProvider) {
        this.apiRepository = apiRepository;
        this.favoritesManager = favoritesManager;
        this.firebaseUserProvider = firebaseUserProvider;
    }

    @Override
    public Observable<MovieDetailResponse> requestMovieData(int movieId, boolean force) {
        if (firebaseUserProvider.get() != null) {
            favoritesManager.fetchAllFavs(); // check for database changes
        }
        if (force && movieInfoDisposable != null) {
            movieInfoDisposable.dispose();
        }
        if (currentId != INITIAL_ID && movieId != currentId && movieInfoDisposable != null) {
            currentId = INITIAL_ID;
            movieInfoDisposable.dispose();
        }
        if (movieInfoDisposable == null || movieInfoDisposable.isDisposed()) {
            movieInfoReplaySubject = ReplaySubject.create();
            currentId = movieId;

            movieInfoDisposable = apiRepository.requestMovieDetails(movieId)
                    .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                    .subscribe(movieInfoReplaySubject::onNext, movieInfoReplaySubject::onError);
        }
        return movieInfoReplaySubject;
    }

    @Override
    public Observable<ResponseOrError<Boolean>> addOrRemoveFromDatabase(int movieId, String posterPath) {
        return favoritesManager.addOrRemoveFromFavs(movieId, posterPath);
    }

    @Override
    public Observable<Boolean> getCurrentState(int movieId) {
        return favoritesManager.getIsFavoriteObservable(movieId);
    }

    @Override
    public boolean getUserState() {
        return firebaseUserProvider.get() != null;
    }
}
