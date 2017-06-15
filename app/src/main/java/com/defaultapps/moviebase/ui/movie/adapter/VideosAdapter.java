package com.defaultapps.moviebase.ui.movie.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movie.VideoResult;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.movie.vh.VideosViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerActivity
public class VideosAdapter extends RecyclerView.Adapter<VideosViewHolder> {

    private List<VideoResult> videos;
    private Context context;
    private OnVideoClickListener listener;

    @Inject
    VideosAdapter(@ActivityContext Context context) {
        this.context = context;
        videos = new ArrayList<>();
    }

    @Override
    public VideosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideosViewHolder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(VideosViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Picasso
                .with(context)
                .load("http://img.youtube.com/vi/"+ videos.get(adapterPosition).getKey() + "/maxresdefault.jpg")
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(view -> listener.onVideoClick(videos.get(adapterPosition).getKey()));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setData(List<VideoResult> videos) {
        this.videos.clear();
        this.videos.addAll(videos);
        notifyDataSetChanged();
    }

    public void setOnVideoClickListener(OnVideoClickListener listener) {
        this.listener = listener;
    }

    public interface OnVideoClickListener {
        void onVideoClick(String videoPath);
    }
}
