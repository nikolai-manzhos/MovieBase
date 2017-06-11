package com.defaultapps.moviebase.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.home.vh.MainViewHolder;
import com.defaultapps.moviebase.ui.home.vh.UpcomingViewHolder;
import com.defaultapps.moviebase.utils.OnMovieSelected;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

/**
 * Created on 5/14/2017.
 */
@PerActivity
public class HomeMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MoviesResponse upcoming;
    private MoviesResponse nowPlaying;

    private Context context;
    private UpcomingAdapter upcomingAdapter;
    private OnMovieSelected listener;

    private final int UPCOMING = 0, NOW_PLAYING = 1;


    @Inject
    public HomeMainAdapter(@ActivityContext Context context,
                           UpcomingAdapter upcomingAdapter) {
        this.context = context;
        this.upcomingAdapter = upcomingAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case UPCOMING:
                View v = inflater.inflate(R.layout.layout_upcoming, parent, false);
                viewHolder = new UpcomingViewHolder(v);
                break;
            case NOW_PLAYING:
                View v1 = inflater.inflate(R.layout.item_now_playing, parent, false);
                viewHolder = new MainViewHolder(v1);
                break;
            default:
                View v2 = inflater.inflate(R.layout.layout_upcoming, parent, false);
                viewHolder = new UpcomingViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        switch (holder.getItemViewType()) {
            case UPCOMING:
                UpcomingViewHolder upcomingViewHolder = (UpcomingViewHolder) holder;
                configureUpcomingVH(upcomingViewHolder, adapterPosition);
                break;
            case NOW_PLAYING:
                MainViewHolder mainViewHolder = (MainViewHolder) holder;
                ((MainViewHolder) holder).nowPlayingContainer.setOnClickListener(view -> listener.onSelect(nowPlaying.getResults().get(adapterPosition -1).getId()));
                configureNowPlayingVH(mainViewHolder, adapterPosition);
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return nowPlaying!= null ? nowPlaying.getResults().size(): 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return UPCOMING;
        } else {
            return NOW_PLAYING;
        }
    }

    private void configureUpcomingVH(UpcomingViewHolder vh, int position) {
        vh.upcomingList.setAdapter(upcomingAdapter);
        vh.upcomingList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        vh.upcomingList.setNestedScrollingEnabled(false);
        upcomingAdapter.setData(upcoming);
    }

    private void configureNowPlayingVH(MainViewHolder vh, int position) {
        vh.rating.setText(String.valueOf(nowPlaying.getResults().get(position - 1).getVoteAverage()));
        vh.title.setText(nowPlaying.getResults().get(position - 1).getTitle());
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w1000/" + nowPlaying.getResults().get(position -1).getBackdropPath())
                .fit()
                .centerCrop()
                .into(vh.backdrop);
    }

    public void setData(List<MoviesResponse> data) {
        upcoming = data.get(0);
        nowPlaying = data.get(1);
        notifyDataSetChanged();
    }

    public void setMovieSelectedListener(OnMovieSelected listener) {
        this.listener = listener;
    }
}
