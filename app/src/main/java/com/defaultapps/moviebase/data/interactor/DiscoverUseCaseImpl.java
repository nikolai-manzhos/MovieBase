package com.defaultapps.moviebase.data.interactor;

import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.local.LocalService;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.ReplayProcessor;


public class DiscoverUseCaseImpl implements DiscoverUseCase {
    private LocalService localService;
    private SchedulerProvider schedulerProvider;

    private Disposable genresDisposable;
    private ReplayProcessor<Genres> genresReplayProcessor;

    @Inject
    public DiscoverUseCaseImpl(LocalService localService,
                               SchedulerProvider schedulerProvider) {
        this.localService = localService;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<Genres> provideGenresList() {
        if (genresDisposable == null || genresDisposable.isDisposed()) {
            genresReplayProcessor = ReplayProcessor.create();

            genresDisposable = Observable.fromCallable(() -> localService.readGenresFromResources())
                    .compose(schedulerProvider.applyIoSchedulers())
                    .subscribe(genresReplayProcessor::onNext, genresReplayProcessor::onError);
        }
        return genresReplayProcessor.toObservable();
    }
}
