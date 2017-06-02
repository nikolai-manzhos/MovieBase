package com.defaultapps.moviebase.data;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AppSchedulerProvider implements SchedulerProvider {

    @Inject
    public AppSchedulerProvider() {}

    @Override
    public <T> ObservableTransformer<T, T> applyIoSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
