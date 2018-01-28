package com.defaultapps.moviebase.di.module;


import com.defaultapps.moviebase.data.repository.ApiRepositoryImpl;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

import dagger.Binds;
import dagger.Module;

@Module
@SuppressWarnings("unused")
public abstract class RepositoryModule {

    @Binds
    abstract ApiRepository bindApiRepository(ApiRepositoryImpl apiRepository);
}
