package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import io.reactivex.Observable;

public interface GenreUseCase {
    Observable<MoviesResponse> requestGenreData(String genreId, boolean force);
}
