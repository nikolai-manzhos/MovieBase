package com.defaultapps.moviebase.data;

import io.reactivex.SingleTransformer;

/**
 * Interface which provides Schedulers to execute on computation thread and read response on main thread.
 */
public interface SchedulerProvider {
    <T> SingleTransformer<T, T> applyIoSchedulers();
}
