package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.base.UseCase;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import java.util.List;

import io.reactivex.Observable;


public interface HomeUseCase extends UseCase {
    Observable<List<MoviesResponse>> requestHomeData(boolean force);
}
