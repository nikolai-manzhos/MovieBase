package com.defaultapps.moviebase.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.home.vh.UpcomingItemViewHolder;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

@PerFragment
public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingItemViewHolder> {

    private MoviesResponse items;
    private Context context;
    private OnMovieClickListener listener;

    @Inject
    public UpcomingAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public UpcomingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_upcoming, parent, false);
        return new UpcomingItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UpcomingItemViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Picasso
                .with(context)
                .load(AppConstants.POSTER_BASE_URL + items.getResults().get(adapterPosition).getPosterPath())
                .fit()
                .into(holder.upcomingImage);
        holder.upcomingContainer.setOnClickListener(view -> listener.onMovieClick(items.getResults().get(adapterPosition).getId()));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.getResults().size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setData(MoviesResponse items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }
}
