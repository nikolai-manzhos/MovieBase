package com.defaultapps.moviebase.ui.movie.vh;


import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;

public class VideosViewHolder extends BaseViewHolder {

    @BindView(R.id.videoThumbnail)
    public ImageView thumbnail;

    public VideosViewHolder(View v) {
        super(v);
    }
}
