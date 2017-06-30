package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;

import io.reactivex.Observable;

public interface PersonUseCase {

    Observable<PersonInfo> requestPersonData(int personId, boolean force);
}
