package com.defaultapps.moviebase.di.module;

import android.content.Context;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.bookmarks.FavoritesAdapter;
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
                                             @ActivityContext Context context) {
        if (dbReference == null) {
            return null;
        }
        return new FavoritesAdapter(dbReference, context);
    }
}
