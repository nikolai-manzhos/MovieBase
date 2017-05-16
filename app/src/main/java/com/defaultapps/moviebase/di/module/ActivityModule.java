package com.defaultapps.moviebase.di.module;

import android.app.Activity;
import android.content.Context;

import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created on 5/14/2017.
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityContext
    @PerActivity
    @Provides
    Context provideActivityContext() {
        return activity;
    }

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
