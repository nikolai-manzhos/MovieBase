package com.defaultapps.moviebase.data;


import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;

public class TestSchedulerProvider implements SchedulerProvider {

    private Scheduler testScheduler;

    public TestSchedulerProvider(Scheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    @Override
    public <T> SingleTransformer<T, T> applyIoSchedulers() {
        return observable -> observable.subscribeOn(testScheduler)
                .observeOn(testScheduler);
    }
}
