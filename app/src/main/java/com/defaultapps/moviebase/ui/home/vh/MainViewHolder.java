package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.nowPlayingHead)
    public CardView nowPlayingContainer;

    @BindView(R.id.movieBackdrop)
    public ImageView backdrop;

    @BindView(R.id.movieRating)
    public TextView rating;

    @BindView(R.id.movieTitle)
    public TextView title;

    public MainViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
