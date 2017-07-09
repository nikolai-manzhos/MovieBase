package com.defaultapps.moviebase;

import android.support.annotation.NonNull;

import com.defaultapps.moviebase.DebugApp;

import com.defaultapps.moviebase.di.component.DaggerApplicationComponent;
import com.defaultapps.moviebase.di.module.UseCaseModule;


public class TestApp extends DebugApp {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder initDaggerAppComponent() {
        return super.initDaggerAppComponent()
                .useCaseModule(new UseCaseModule());
    }
}
