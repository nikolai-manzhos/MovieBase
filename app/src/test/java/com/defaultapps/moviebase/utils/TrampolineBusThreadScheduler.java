package com.defaultapps.moviebase.utils;

import com.defaultapps.moviebase.utils.rx.BusThreadScheduler;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TrampolineBusThreadScheduler implements BusThreadScheduler {

    @Override
    public Scheduler getScheduler() {
        return Schedulers.trampoline();
    }
}
