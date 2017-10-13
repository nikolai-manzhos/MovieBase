package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.utils.ResponseOrError;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MovieUseCase {
    Observable<MovieInfoResponse> requestMovieData(int movieId, boolean force);
    Observable<ResponseOrError<Boolean>> addOrRemoveFromDatabase(int movieId, String posterPath);
    Observable<Boolean> getCurrentState(int movieId);
    boolean getUserState();
}
