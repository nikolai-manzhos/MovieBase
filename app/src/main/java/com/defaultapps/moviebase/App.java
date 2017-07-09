package com.defaultapps.moviebase;

import android.app.Application;
import android.support.annotation.NonNull;

import com.defaultapps.moviebase.di.component.ApplicationComponent;
import com.defaultapps.moviebase.di.component.DaggerApplicationComponent;
import com.defaultapps.moviebase.di.module.ApplicationModule;
import com.defaultapps.preferenceshelper.PreferencesHelper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;


public class App extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initPrefs();
        appComponent = initDaggerAppComponent().build();
        initIconify();
        initFirebase();
    }

    public ApplicationComponent getAppComponent() {
        return this.appComponent;
    }

    @NonNull
    protected DaggerApplicationComponent.Builder initDaggerAppComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    private void initIconify() {
        Iconify
                .with(new MaterialModule());
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void initPrefs() {
        new PreferencesHelper.Builder(this)
                .build();
    }
}
