package com.defaultapps.moviebase.di.component;

import com.defaultapps.moviebase.di.module.FragmentModule;
import com.defaultapps.moviebase.di.module.UseCaseModule;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.bookmarks.BookmarksViewImpl;
import com.defaultapps.moviebase.ui.discover.DiscoverViewImpl;
import com.defaultapps.moviebase.ui.genre.GenreViewImpl;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;
import com.defaultapps.moviebase.ui.movie.MovieViewImpl;
import com.defaultapps.moviebase.ui.person.PersonViewImpl;
import com.defaultapps.moviebase.ui.search.SearchViewImpl;
import com.defaultapps.moviebase.ui.user.UserViewImpl;

import dagger.Component;

@PerFragment
@Component(dependencies = {ActivityComponent.class}, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomeViewImpl homeView);
    void inject(DiscoverViewImpl discoverView);
    void inject(BookmarksViewImpl bookmarksView);
    void inject(GenreViewImpl genreView);
    void inject(MovieViewImpl movieView);
    void inject(UserViewImpl userView);
    void inject(SearchViewImpl searchView);
    void inject(PersonViewImpl staffView);
}
