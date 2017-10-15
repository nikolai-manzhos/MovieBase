package com.defaultapps.moviebase.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.di.ApplicationContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    @Singleton
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    AssetManager provideAssetManager() {
        return application.getAssets();
    }

    @Provides
    @Nullable
    FirebaseUser provideFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    @Provides
    @Nullable
    DatabaseReference provideFirebaseReference(@Nullable FirebaseUser loggedUser) {
        if (loggedUser == null) {
            return null;
        }
        return FirebaseDatabase.getInstance().getReference().child("users").child(loggedUser.getUid());
    }
}
