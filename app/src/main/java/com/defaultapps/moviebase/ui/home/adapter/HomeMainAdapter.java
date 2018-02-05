package com.defaultapps.moviebase.ui.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.movies.Result;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.home.vh.MainViewHolder;
import com.defaultapps.moviebase.ui.home.vh.UpcomingViewHolder;
import com.defaultapps.moviebase.utils.ResUtils;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;


@PerFragment
public class HomeMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int UPCOMING = 0, NOW_PLAYING = 1;

    private MoviesResponse upcoming;
    private MoviesResponse nowPlaying;

    private final Context context;
    private final UpcomingAdapter upcomingAdapter;
    private final ResUtils resUtils;
    private OnMovieClickListener listener;

    @Inject
    HomeMainAdapter(@ActivityContext Context context,
                    UpcomingAdapter upcomingAdapter,
                    ResUtils resUtils) {
        this.context = context;
        this.upcomingAdapter = upcomingAdapter;
        this.resUtils = resUtils;
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
                int id = nowPlaying.getResults().get(adapterPosition - 1).getId();
                ((MainViewHolder) holder).nowPlayingContainer
                        .setOnClickListener(view -> listener.onMovieClick(id));
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

    @SuppressWarnings("unused")
    private void configureUpcomingVH(UpcomingViewHolder vh, int position) {
        vh.upcomingList.setAdapter(upcomingAdapter);
        vh.upcomingList.setLayoutManager(new LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false));
        vh.upcomingList.setNestedScrollingEnabled(false);
        upcomingAdapter.setData(upcoming);
    }

    @SuppressLint("SetTextI18n")
    private void configureNowPlayingVH(MainViewHolder vh, int position) {
        Result result = nowPlaying.getResults().get(position - 1);
        vh.rating.setText(result.getVoteAverage() + " " + resUtils.getString(R.string.star_icon));
        vh.title.setText(result.getTitle());
        final String backdropPath = result.getBackdropPath();
        Picasso
                .with(context)
                .load("http://image.tmdb.org/t/p//w1280" + backdropPath)
                .fit()
                .centerCrop()
                .into(vh.backdrop);
    }

    public void setData(List<MoviesResponse> data) {
        upcoming = data.get(0);
        nowPlaying = data.get(1);
        notifyDataSetChanged();
    }

    public void setMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }
}
