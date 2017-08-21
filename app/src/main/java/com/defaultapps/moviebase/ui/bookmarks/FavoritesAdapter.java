package com.defaultapps.moviebase.ui.bookmarks;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends FirebaseRecyclerAdapter<Favorite, FavoritesAdapter.FavoritesViewHolder> {

    private Context context;
    private OnMovieClickListener listener;

    @SuppressWarnings("WeakerAccess")
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favoritePoster)
        ImageView favoritePoster;

        public FavoritesViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
    @Inject
    FavoritesAdapter(DatabaseReference dbReference, @ActivityContext Context context) {
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
                .centerInside()
                .into(viewHolder.favoritePoster);
        viewHolder.favoritePoster.setOnClickListener(view -> listener.onMovieClick(model.getFavoriteMovieId()));
        viewHolder.favoritePoster.setOnLongClickListener(view -> {
            showAlertDialog(context.getString(R.string.bookmarks_alert_title), null,
                    (dialogInterface, which) -> {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            reference.removeValue();
                        }
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialogInterface.dismiss();
                        }
                    });
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("deprecation")
    private void showAlertDialog(String title, @Nullable String  message,
                                   DialogInterface.OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_ok, listener)
                .setNegativeButton(R.string.alert_cancel, listener)
                .show();
        int accentColor = context.getResources().getColor(R.color.colorAccent);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(accentColor);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(accentColor);
    }
}
