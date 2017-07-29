package com.defaultapps.moviebase.utils;

import com.defaultapps.moviebase.utils.rx.ThreadScheduler;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class TrampolineThreadScheduler implements ThreadScheduler {

    @Override
    public Scheduler getScheduler() {
        return Schedulers.trampoline();
    }
}
