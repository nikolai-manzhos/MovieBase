package com.defaultapps.moviebase.di.module;

import android.content.Context;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.FragmentContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.bookmarks.FavoritesAdapter;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    /**
     * Checks if #DatabaseReference is null.
     * @param dbReference Reference to logged user db
     * @param context Host's activity context
     * @return FavoritesAdapter or null if user in not logged
     */
    @PerFragment
    @Provides
    @Nullable
    FavoritesAdapter provideFavoritesAdapter(@Nullable DatabaseReference dbReference,
                                             @ActivityContext Context context,
                                             @FragmentContext DefaultNavigator defaultNavigator,
                                             ViewUtils viewUtils) {
        if (dbReference == null) {
            return null;
        }
        FirebaseRecyclerOptions<Favorite> options =
                new FirebaseRecyclerOptions.Builder<Favorite>()
                        .setQuery(dbReference, Favorite.class)
                        .build();
        return new FavoritesAdapter(options, context, defaultNavigator, viewUtils);
    }

    /**
     * Provide DefaultNavigator which is tied to Fragment lifecycle
     * @return instance of {#com.}
     */
    @FragmentContext
    @PerFragment
    @Provides
    DefaultNavigator provideDefaultNavigator() {
        return new DefaultNavigator<>();
    }
}
