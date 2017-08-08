package com.defaultapps.moviebase.utils.rx;

import io.reactivex.Scheduler;

public interface BusThreadScheduler {
    Scheduler getScheduler();
}
