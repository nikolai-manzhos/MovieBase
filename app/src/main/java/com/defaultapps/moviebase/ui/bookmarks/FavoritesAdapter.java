package com.defaultapps.moviebase.ui.bookmarks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends FirebaseRecyclerAdapter<Favorite, FavoritesAdapter.FavoritesViewHolder> {

    private Context context;
    private OnMovieClickListener listener;
    private OnMovieLongClickListener longClickListener;

    @SuppressWarnings("WeakerAccess")
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favoritePoster)
        ImageView favoritePoster;

        public FavoritesViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    FavoritesAdapter(DatabaseReference dbReference, Context context) {
        super(Favorite.class, R.layout.item_favorite, FavoritesViewHolder.class, dbReference);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(FavoritesViewHolder viewHolder, Favorite model, int position) {
        int aPosition = viewHolder.getAdapterPosition();
        DatabaseReference reference = getRef(aPosition).getRef();
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + model.getPosterPath())
                .fit()
                .centerCrop()
                .into(viewHolder.favoritePoster);
        viewHolder.favoritePoster.setOnClickListener(view -> listener.onMovieClick(model.getFavoriteMovieId()));
        viewHolder.favoritePoster.setOnLongClickListener(view -> {
//            longClickListener.onLongClick(key);
            reference.removeValue();
            return true;
        });
    }

    void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    void setLongClickListener(OnMovieLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    interface OnMovieLongClickListener {
        boolean onLongClick(String key);
    }
}
