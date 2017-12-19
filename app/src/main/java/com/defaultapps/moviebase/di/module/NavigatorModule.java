package com.defaultapps.moviebase.di.module;

import com.defaultapps.moviebase.ui.login.LoginContract;
import com.defaultapps.moviebase.ui.login.LoginNavigatorImpl;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class NavigatorModule {

    @Binds
    abstract LoginContract.LoginNavigator bindLoginNavigator(LoginNavigatorImpl loginNavigator);
}
