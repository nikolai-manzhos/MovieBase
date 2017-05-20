package com.defaultapps.moviebase.data.repository;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import io.reactivex.Observable;

/**
 * Created on 5/20/2017.
 */

public interface DiscoverUseCase {

    Observable<Genres> provideGenresList();
}
