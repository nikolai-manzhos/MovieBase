package com.defaultapps.moviebase;

import android.app.Application;

import com.defaultapps.moviebase.di.component.ApplicationComponent;
import com.defaultapps.moviebase.di.component.DaggerApplicationComponent;
import com.defaultapps.moviebase.di.module.ApplicationModule;

/**
 * Created on 5/13/2017.
 */

public class App extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerAppComponent();
    }

    public ApplicationComponent getAppComponent() {
        return this.appComponent;
    }

    private void initDaggerAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
