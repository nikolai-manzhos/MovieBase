package com.defaultapps.moviebase.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

import com.defaultapps.moviebase.data.AppSchedulerProvider;
import com.defaultapps.moviebase.data.SchedulerProvider;
import com.defaultapps.moviebase.data.firebase.LoggedUser;
import com.defaultapps.moviebase.di.ApplicationContext;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @ApplicationContext
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    AssetManager provideAssetManager() {
        return application.getAssets();
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Singleton
    @Provides
    DatabaseReference provideFirebaseReference(LoggedUser loggedUser) {
        return FirebaseDatabase.getInstance().getReference().child("users").child(loggedUser.getUserId());
    }
}
