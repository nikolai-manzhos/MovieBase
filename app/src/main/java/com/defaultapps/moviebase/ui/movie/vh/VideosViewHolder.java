package com.defaultapps.moviebase.ui.movie.vh;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideosViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.videoThumbnail)
    public ImageView thumbnail;

    public VideosViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
