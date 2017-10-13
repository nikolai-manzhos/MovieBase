package com.defaultapps.moviebase.data.usecase;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class SearchUseCaseImpl implements SearchUseCase {

    private final NetworkService networkService;
    private final AppPreferencesManager preferencesManager;
    private final SchedulerProvider schedulerProvider;

    private Disposable disposable;
    private Disposable paginationDisposable;
    private ReplaySubject<MoviesResponse> replaySubject;

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

            disposable = network(query, 1)
                    .compose(schedulerProvider.applyIoSchedulers())
                    .subscribe(replaySubject::onNext, replaySubject::onError);
        }
        return replaySubject;
    }

    @Override
    public Observable<MoviesResponse> requestMoreSearchResults(String query) {
        if (paginationDisposable != null && !paginationDisposable.isDisposed()) {
            paginationDisposable.dispose();
        }
        MoviesResponse previousResult = replaySubject.getValue();
        PublishSubject<MoviesResponse> paginationResult = PublishSubject.create();
        paginationDisposable = network(query, previousResult.getPage() + 1)
                .map(moviesResponse -> {
                    previousResult.getResults().addAll(moviesResponse.getResults());
                    previousResult.setPage(moviesResponse.getPage());
                    return previousResult;
                })
                .compose(schedulerProvider.applyIoSchedulers())
                .subscribe(
                        response -> {
                            paginationResult.onNext(response);
                            replaySubject.onNext(response);
                        },
                        paginationResult::onError
                );
        return paginationResult;
    }

    private Single<MoviesResponse> network(String query, int page) {
        String API_KEY = BuildConfig.MDB_API_KEY;
        return networkService.getNetworkCall().getSearchQuery(API_KEY, "en-Us", query, page, preferencesManager.getAdultStatus());
    }
}
