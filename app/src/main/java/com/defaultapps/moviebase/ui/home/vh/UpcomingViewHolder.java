package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;

public class UpcomingViewHolder extends BaseViewHolder {

    @BindView(R.id.upcomingRecycler)
    public RecyclerView upcomingList;

    public UpcomingViewHolder(View view) {
        super(view);
    }
}
