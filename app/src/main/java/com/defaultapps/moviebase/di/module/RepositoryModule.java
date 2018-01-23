package com.defaultapps.moviebase.di.module;


import com.defaultapps.moviebase.data.repository.ApiRepository;
import com.defaultapps.moviebase.data.repository.ApiRepositoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {

    @Binds
    abstract ApiRepository bindApiRepository(ApiRepositoryImpl apiRepository);
}
