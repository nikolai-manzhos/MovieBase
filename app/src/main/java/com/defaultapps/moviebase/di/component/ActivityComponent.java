package com.defaultapps.moviebase.di.component;
import com.defaultapps.moviebase.di.module.ActivityModule;
import com.defaultapps.moviebase.di.module.PresenterModule;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PresenterModule.class})
public interface ActivityComponent {
    FragmentComponent plusFragmentComponent();

    void inject(MainActivity mainActivity);
}
