package com.defaultapps.moviebase;

import android.support.annotation.NonNull;

import com.defaultapps.moviebase.data.firebase.FirebaseService;
import com.defaultapps.moviebase.di.component.ApplicationComponent;

import static org.mockito.Mockito.mock;


public class FakeApplication extends App {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        appComponent = mock(ApplicationComponent.class);
        firebaseService = mock(FirebaseService.class);
        super.onCreate();
    }

    @NonNull
    @Override
    protected ApplicationComponent initDaggerAppComponent() {
        return appComponent;
    }
}
