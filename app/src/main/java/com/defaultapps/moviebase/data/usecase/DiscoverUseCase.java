package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.base.UseCase;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import io.reactivex.Observable;


public interface DiscoverUseCase extends UseCase {

    Observable<Genres> provideGenresList();
}
