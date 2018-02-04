package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.domain.base.UseCase;

import java.util.List;

import io.reactivex.Observable;


public interface HomeUseCase extends UseCase {
    Observable<List<MoviesResponse>> requestHomeData(boolean force);
}
