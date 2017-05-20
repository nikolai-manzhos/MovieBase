package com.defaultapps.moviebase;

import android.app.Application;

import com.defaultapps.moviebase.di.component.ApplicationComponent;
import com.defaultapps.moviebase.di.component.DaggerApplicationComponent;
import com.defaultapps.moviebase.di.module.ApplicationModule;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

public class App extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerAppComponent();
        initIconify();
    }

    public ApplicationComponent getAppComponent() {
        return this.appComponent;
    }

    private void initDaggerAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    private void initIconify() {
        Iconify
                .with(new MaterialModule());
    }
}
