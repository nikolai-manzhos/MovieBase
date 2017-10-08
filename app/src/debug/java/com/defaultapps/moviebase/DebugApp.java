package com.defaultapps.moviebase;

import com.squareup.leakcanary.LeakCanary;

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
}
