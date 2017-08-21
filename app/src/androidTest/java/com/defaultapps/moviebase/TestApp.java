package com.defaultapps.moviebase;

import android.support.annotation.NonNull;

import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.di.component.DaggerApplicationComponent;
import com.defaultapps.moviebase.di.module.SchedulersModule;
import com.defaultapps.moviebase.espresso.LogLevel;
import com.defaultapps.moviebase.espresso.RxEspresso;

import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class TestApp extends DebugApp {

    @Override
    public void onCreate() {
        super.onCreate();
        RxEspresso.setLogLevel(LogLevel.DEBUG);
    }

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder initDaggerAppComponent() {
        return super.initDaggerAppComponent()
                .schedulersModule(new SchedulersModule() {
                    @Singleton
                    @Provides
                    SchedulerProvider provideSchedulerProvider() {
                        return new SchedulerProvider() {
                            @Override
                            public <T> SingleTransformer<T, T> applyIoSchedulers() {
                                return single ->
                                        single.observeOn(Schedulers.io())
                                        .subscribeOn(AndroidSchedulers.mainThread())
                                        .doOnSubscribe(disposable -> RxEspresso.increment())
                                        .doAfterTerminate(RxEspresso::decrement);
                            }
                        };
                    }

//                    @Singleton
//                    @Provides
//                    ThreadScheduler provideThreadScheduler() {
//                        return new MainThreadScheduler();
//                    }
                });}
}
