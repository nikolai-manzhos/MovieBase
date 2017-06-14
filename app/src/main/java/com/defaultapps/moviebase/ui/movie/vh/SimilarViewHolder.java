package com.defaultapps.moviebase.ui.movie.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SimilarViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.moviePoster)
    public ImageView moviePoster;

    public SimilarViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
