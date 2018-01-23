package com.defaultapps.moviebase.domain.base;

import io.reactivex.disposables.CompositeDisposable;

public class BaseUseCase implements UseCase {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
