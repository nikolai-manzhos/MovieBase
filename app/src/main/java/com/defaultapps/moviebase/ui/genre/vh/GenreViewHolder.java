package com.defaultapps.moviebase.ui.genre.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GenreViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.genreContainer)
    public RelativeLayout container;

    @BindView(R.id.moviePoster)
    public ImageView poster;

    @BindView(R.id.movieTitle)
    public TextView title;

    @BindView(R.id.movieDate)
    public IconTextView movieDate;

    @BindView(R.id.movieRating)
    public IconTextView movieRating;

    public GenreViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}