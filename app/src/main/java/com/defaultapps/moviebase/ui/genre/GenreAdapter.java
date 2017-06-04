package com.defaultapps.moviebase.ui.genre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private Context context;
    private MoviesResponse items;

    @Inject
    GenreAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.moviePoster)
        ImageView poster;

        @BindView(R.id.movieTitle)
        TextView title;

        GenreViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GenreViewHolder(LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false));
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        String posterPath = items.getResults().get(adapterPosition).getPosterPath();
        holder.title.setText(items.getResults().get(adapterPosition).getTitle());
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + posterPath)
                .fit()
                .centerCrop()
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.getResults().size() : 0;
    }

    public void setData(MoviesResponse items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
