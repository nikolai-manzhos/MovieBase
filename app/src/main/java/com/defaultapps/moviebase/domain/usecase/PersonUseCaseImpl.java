package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.models.responses.person.PersonResponse;
import com.defaultapps.moviebase.domain.base.BaseUseCase;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class PersonUseCaseImpl extends BaseUseCase implements PersonUseCase {

    private final ApiRepository apiRepository;

    private ReplaySubject<PersonResponse> personReplaySubject;
    private Disposable personDisposable;

    @Inject
    PersonUseCaseImpl(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @Override
    public Observable<PersonResponse> requestPersonData(int personId, boolean force) {
        if (force && personDisposable != null) {
            personDisposable.dispose();
        }
        if (personDisposable == null || personDisposable.isDisposed()) {
            personReplaySubject = ReplaySubject.create();

            personDisposable = apiRepository.requestPersonDetails(personId)
                    .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                    .subscribe(personReplaySubject::onNext, personReplaySubject::onError);
        }
        return personReplaySubject;
    }
}
