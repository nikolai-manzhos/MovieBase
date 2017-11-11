package com.defaultapps.moviebase.ui.bookmarks;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.firebase.Favorite;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**Constructed with dagger.
 * See: {@link com.defaultapps.moviebase.di.module.FragmentModule}
 */
public class FavoritesAdapter extends FirebaseRecyclerAdapter<Favorite, FavoritesAdapter.FavoritesViewHolder> {

    private final Context context;
    private final ViewUtils viewUtils;
    private OnMovieClickListener listener;


    public FavoritesAdapter(FirebaseRecyclerOptions<Favorite> options,
                            @ActivityContext Context context,
                            ViewUtils viewUtils) {
        super(options);
        this.context = context;
        this.viewUtils = viewUtils;
    }

    @SuppressWarnings("WeakerAccess")
    public static class FavoritesViewHolder extends BaseViewHolder {
        @BindView(R.id.favoritePoster)
        ImageView favoritePoster;

        public FavoritesViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavoritesViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(FavoritesViewHolder holder, int position, Favorite model) {
        int aPosition = holder.getAdapterPosition();
        DatabaseReference reference = getRef(aPosition).getRef();
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + model.getPosterPath())
                .fit()
                .centerInside()
                .into(holder.favoritePoster);
        holder.favoritePoster.setOnClickListener(view -> listener.onMovieClick(model.getFavoriteMovieId()));
        holder.favoritePoster.setOnLongClickListener(view -> {
            viewUtils.showAlertDialog(context.getString(R.string.bookmarks_alert_title), null,
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

    void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }
}
