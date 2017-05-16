package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 5/16/2017.
 */

public class UpcomingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.upcomingRecycler)
    public RecyclerView upcomingList;

    public UpcomingViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
