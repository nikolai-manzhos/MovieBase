package com.defaultapps.moviebase.ui.movie;

import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;

import javax.inject.Inject;

@PerFragment
public class MovieNavigatorImpl extends DefaultNavigator<MovieContract.MovieView> {

    @Inject
    MovieNavigatorImpl() {}
}
