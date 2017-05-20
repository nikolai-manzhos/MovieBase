package com.defaultapps.moviebase.data.repository;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created on 5/14/2017.
 */

public interface HomeUseCase {
    Observable<List<MoviesResponse>> requestHomeData(boolean force);
}
