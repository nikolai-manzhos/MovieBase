package com.defaultapps.moviebase;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;

import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.di.component.ApplicationComponent;
import com.defaultapps.moviebase.di.component.DaggerApplicationComponent;
import com.defaultapps.moviebase.di.module.ApplicationModule;
import com.defaultapps.preferenceshelper.PreferencesHelper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

import javax.inject.Inject;


@SuppressLint("Registered")
public class App extends Application {

    private ApplicationComponent appComponent;

    @Inject
    FirebaseService firebaseService;

    @Override
    public void onCreate() {
        super.onCreate();
        initPrefs();
        initIconify();
        initFirebase();
        appComponent = initDaggerAppComponent().build();
        appComponent.inject(this);
        initRemoteConfig();
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
        PreferencesHelper.builder(this)
                .build();
    }

    private void initRemoteConfig() {
        firebaseService.setDefaultRemoteConfigValues();
        firebaseService.fetchRemoteConfig();
        firebaseService.getBlockedMoviesId();
    }
}
