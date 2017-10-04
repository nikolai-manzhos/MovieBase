package com.defaultapps.moviebase.data.usecase;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import io.reactivex.Observable;

public interface SearchUseCase {
    Observable<MoviesResponse> requestSearchResults(String query, boolean force);
    Observable<MoviesResponse> requestMoreSearchResults(String query);
}
