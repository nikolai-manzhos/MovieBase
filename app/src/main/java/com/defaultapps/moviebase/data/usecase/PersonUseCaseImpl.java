package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.base.BaseUseCase;
import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class PersonUseCaseImpl extends BaseUseCase implements PersonUseCase {

    private final NetworkService networkService;
    private final SchedulerProvider schedulerProvider;

    private ReplaySubject<PersonInfo> personReplaySubject;
    private Disposable personDisposable;

    @Inject
    PersonUseCaseImpl(NetworkService networkService,
                             SchedulerProvider schedulerProvider) {
        this.networkService = networkService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<PersonInfo> requestPersonData(int personId, boolean force) {
        if (force && personDisposable != null) {
            personDisposable.dispose();
        }
        if (personDisposable == null || personDisposable.isDisposed()) {
            personReplaySubject = ReplaySubject.create();

            personDisposable = network(personId)
                    .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                    .subscribe(personReplaySubject::onNext, personReplaySubject::onError);
        }
        return personReplaySubject;
    }

    private Single<PersonInfo> network(int personId) {
        String API_KEY = BuildConfig.MDB_API_KEY;
        return networkService.getNetworkCall().getPersonInfo(personId, API_KEY, "en-Us", "movie_credits")
                .compose(schedulerProvider.applyIoSchedulers());
    }
}
