package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import java.util.List;

import io.reactivex.Observable;


public interface HomeUseCase {
    Observable<List<MoviesResponse>> requestHomeData(boolean force);
}