package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.domain.base.UseCase;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import io.reactivex.Observable;


public interface DiscoverUseCase extends UseCase {

    Observable<Genres> provideGenresList();
}
