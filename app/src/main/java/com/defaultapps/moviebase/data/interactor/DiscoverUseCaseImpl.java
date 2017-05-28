package com.defaultapps.moviebase.data.interactor;


import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

@Singleton
public class DiscoverUseCaseImpl implements DiscoverUseCase {
    private LocalService localService;
    private SchedulerProvider schedulerProvider;

    private Disposable genresDisposable;
    private ReplaySubject<Genres> genresReplaySubject;
    private Genres memoryCache;

    @Inject
    DiscoverUseCaseImpl(LocalService localService,
                               SchedulerProvider schedulerProvider) {
        this.localService = localService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<Genres> provideGenresList() {
        if (genresDisposable == null || genresDisposable.isDisposed()) {
            genresReplaySubject = ReplaySubject.create();

            genresDisposable = Observable.concat(memory(), local())
                    .filter(genres -> genres.getGenres() != null).firstOrError()
                    .subscribe(genresReplaySubject::onNext, genresReplaySubject::onError);
        }
        return genresReplaySubject;
    }

    private Observable<Genres> local() {
        return Observable.fromCallable(() -> localService.readGenresFromResources())
                .doOnNext(genres -> memoryCache = genres)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    private Observable<Genres> memory() {
        if (memoryCache != null) {
            return Observable.just(memoryCache);
        }
        return Observable.just(new Genres());
    }
}
