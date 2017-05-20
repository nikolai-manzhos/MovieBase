package com.defaultapps.moviebase.ui.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private Context context;
    private Genres genres;

    @Inject
    public DiscoverAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    static class DiscoverViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.discoverImage)
        ImageView image;

        @BindView(R.id.discoverGenre)
        TextView genreName;

        DiscoverViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoverViewHolder(LayoutInflater.from(context).inflate(R.layout.item_discover, parent, false));
    }

    @Override
    public void onBindViewHolder(DiscoverViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        holder.genreName.setText(genres.getGenres().get(adapterPosition).getName());
        Picasso
                .with(context)
                .load(R.drawable.action)
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return genres != null ? genres.getGenres().size() : 0;
    }

    public void setData(Genres genres) {
        this.genres = genres;
        notifyDataSetChanged();
    }
}
