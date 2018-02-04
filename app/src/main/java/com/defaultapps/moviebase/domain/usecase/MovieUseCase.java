package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.domain.base.UseCase;
import com.defaultapps.moviebase.utils.ResponseOrError;

import io.reactivex.Observable;

public interface MovieUseCase extends UseCase {
    Observable<MovieDetailResponse> requestMovieData(int movieId, boolean force);
    Observable<ResponseOrError<Boolean>> addOrRemoveFromDatabase(int movieId, String posterPath);
    Observable<Boolean> getCurrentState(int movieId);
    boolean getUserState();
}
