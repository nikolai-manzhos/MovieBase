package com.defaultapps.moviebase;

import android.support.annotation.NonNull;

import com.defaultapps.moviebase.App;
import com.defaultapps.moviebase.di.component.ApplicationComponent;

import static org.mockito.Mockito.mock;


public class FakeApplication extends App {

    @NonNull
    private ApplicationComponent appComponent = mock(ApplicationComponent.class);

    @Override
    @NonNull
    public ApplicationComponent getAppComponent() {
        return appComponent;
    }
}
