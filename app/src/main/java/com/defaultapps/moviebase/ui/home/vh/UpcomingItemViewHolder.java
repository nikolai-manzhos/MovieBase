package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;


public class UpcomingItemViewHolder extends BaseViewHolder {

    @BindView(R.id.upcomingContainer)
    public CardView upcomingContainer;

    @BindView(R.id.upcomingImage)
    public ImageView upcomingImage;

    public UpcomingItemViewHolder(View view) {
        super(view);
    }
}
