package com.defaultapps.moviebase.data;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Interface which provides Schedulers to execute on computation thread and read response on main thread.
 */
public interface SchedulerProvider {
    <T> ObservableTransformer<T, T> applyIoSchedulers();
}
