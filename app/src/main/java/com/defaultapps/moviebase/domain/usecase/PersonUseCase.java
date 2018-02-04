package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.models.responses.person.PersonResponse;

import io.reactivex.Observable;

public interface PersonUseCase {

    Observable<PersonResponse> requestPersonData(int personId, boolean force);
}
