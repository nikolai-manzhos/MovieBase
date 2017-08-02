package com.defaultapps.moviebase.ui.movie.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.Result;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.movie.vh.SimilarViewHolder;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerFragment
public class SimilarAdapter extends RecyclerView.Adapter<SimilarViewHolder> {

    private Context context;
    private List<Result> similarMovies;
    private OnMovieClickListener listener;

    @Inject
    SimilarAdapter(@ActivityContext Context context) {
        this.context = context;
        similarMovies = new ArrayList<>();
    }

    @Override
    public SimilarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimilarViewHolder(LayoutInflater.from(context).inflate(R.layout.item_similar, parent, false));
    }

    @Override
    public void onBindViewHolder(SimilarViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Picasso
                .with(context)
                .load(AppConstants.POSTER_BASE_URL + similarMovies.get(adapterPosition).getPosterPath())
                .fit()
                .into(holder.moviePoster);
        holder.moviePoster.setOnClickListener(view -> listener.onMovieClick(similarMovies.get(adapterPosition).getId()));
    }

    @Override
    public int getItemCount() {
        return similarMovies.size();
    }

    public void setData(List<Result> similarMovies) {
        this.similarMovies.clear();
        this.similarMovies.addAll(similarMovies);
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }
}
