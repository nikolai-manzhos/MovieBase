package com.defaultapps.moviebase;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Debug application.
 * Replaced with {@link App} on release.
 */
public class DebugApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
//        initStetho();
        initLeakCanary();
        initCrashlytics();
        Timber.plant(new Timber.DebugTree());
    }

//    private void initStetho() {
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
//                        .build());
//    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    private void initCrashlytics() {
        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
                .disabled(true)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());
    }
}
