package com.defaultapps.moviebase.domain.usecase;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import io.reactivex.Observable;

public interface GenreUseCase {
    Observable<MoviesResponse> requestGenreData(String genreId, boolean force);
    Observable<MoviesResponse> requestMoreGenreData(String genreId);
}
