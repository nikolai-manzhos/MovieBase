package com.defaultapps.moviebase.data.interactor;

import com.defaultapps.moviebase.data.network.NetworkService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchUseCaseImpl implements SearchUseCase {

    private NetworkService networkService;

    @Inject
    public SearchUseCaseImpl(NetworkService networkService) {
        this.networkService = networkService;
    }
}
