package com.defaultapps.moviebase.ui.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.searchContainer)
    public RelativeLayout searchContainer;

    @BindView(R.id.moviePoster)
    public ImageView moviePoster;

    @BindView(R.id.movieTitle)
    public TextView movieTitle;

    @BindView(R.id.movieDate)
    public TextView movieDate;

    public SearchViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
