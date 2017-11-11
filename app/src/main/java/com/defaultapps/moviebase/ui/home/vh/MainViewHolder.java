package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;


public class MainViewHolder extends BaseViewHolder {

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
    }
}
