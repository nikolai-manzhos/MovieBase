package com.defaultapps.moviebase.data.interactor;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import io.reactivex.Observable;

public interface SearchUseCase {
    Observable<MoviesResponse> requestSearchResults(String query, boolean force);
}
