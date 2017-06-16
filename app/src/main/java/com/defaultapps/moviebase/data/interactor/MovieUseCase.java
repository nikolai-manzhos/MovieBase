package com.defaultapps.moviebase.data.interactor;

import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;

import io.reactivex.Observable;

public interface MovieUseCase {
    Observable<MovieInfoResponse> requestMovieData(int movieId, boolean force);
    Observable<Boolean> addMovieToDatabase(int movieId, String posterPath);
}
