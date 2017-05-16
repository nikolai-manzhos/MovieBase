package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/17/2017.
 */

public class UpcomingItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.upcomingImage)
    public ImageView upcomingImage;

    public UpcomingItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
