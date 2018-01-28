package com.defaultapps.moviebase.domain.usecase;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.domain.base.BaseUseCase;
import com.defaultapps.moviebase.domain.repository.ApiRepository;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.rx.RxBus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;


@Singleton
public class HomeUseCaseImpl extends BaseUseCase implements HomeUseCase {

    private final ApiRepository apiRepository;
    private final RxBus rxBus;

    private Disposable moviesDisposable;
    private BehaviorSubject<List<MoviesResponse>> moviesBehaviorSubject;
    private List<MoviesResponse> cache;

    @Inject
    HomeUseCaseImpl(ApiRepository apiRepository,
                    RxBus rxBus) {
        this.apiRepository = apiRepository;
        this.rxBus = rxBus;
    }

    @Override
    public Observable<List<MoviesResponse>> requestHomeData(boolean force) {
        if (cache != null
                && moviesBehaviorSubject != null
                && !moviesBehaviorSubject.hasValue()
                && !moviesBehaviorSubject.hasThrowable()) {
            rxBus.publish(AppConstants.HOME_INSTANT_CACHE, cache);
        }
        if (force && moviesDisposable != null) {
            moviesDisposable.dispose();
        } else if (!force
                && cache != null
                && moviesBehaviorSubject != null
                && moviesBehaviorSubject.hasThrowable()) {
            rxBus.publish(AppConstants.HOME_INSTANT_CACHE, cache);
            return Observable.empty();
        } else if (!force
                && cache == null
                && moviesBehaviorSubject != null
                && moviesBehaviorSubject.hasThrowable()) {
            moviesDisposable.dispose();
        }
        if (moviesDisposable == null || moviesDisposable.isDisposed()) {
            moviesBehaviorSubject = BehaviorSubject.create();

            moviesDisposable = apiRepository.requestHomeData()
                    .doOnSubscribe(disposable -> getCompositeDisposable().add(disposable))
                    .doOnSuccess(moviesList -> cache = moviesList)
                    .subscribe(moviesBehaviorSubject::onNext, moviesBehaviorSubject::onError);
        }
        return moviesBehaviorSubject;
    }
}
