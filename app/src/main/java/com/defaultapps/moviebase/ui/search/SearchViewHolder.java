package com.defaultapps.moviebase.ui.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;

public class SearchViewHolder extends BaseViewHolder {
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
    }
}
