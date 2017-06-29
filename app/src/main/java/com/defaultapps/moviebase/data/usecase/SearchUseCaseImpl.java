package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class SearchUseCaseImpl implements SearchUseCase {

    private NetworkService networkService;
    private AppPreferencesManager preferencesManager;
    private SchedulerProvider schedulerProvider;
    private Disposable disposable;
    private ReplaySubject<MoviesResponse> replaySubject;

    private final String API_KEY = BuildConfig.MDB_API_KEY;

    @Inject
    SearchUseCaseImpl(NetworkService networkService,
                             SchedulerProvider schedulerProvider,
                             AppPreferencesManager preferencesManager) {
        this.networkService = networkService;
        this.schedulerProvider = schedulerProvider;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public Observable<MoviesResponse> requestSearchResults(String query, boolean force) {
        if (force && disposable != null) disposable.dispose();
        if (disposable == null || disposable.isDisposed()) {
            replaySubject = ReplaySubject.create();

            disposable = networkService.getNetworkCall().getSearchQuery(API_KEY, "en-Us", query, 1, preferencesManager.getAdultStatus())
                    .compose(schedulerProvider.applyIoSchedulers())
                    .subscribe(replaySubject::onNext, replaySubject::onError);
        }
        return replaySubject;
    }
}
