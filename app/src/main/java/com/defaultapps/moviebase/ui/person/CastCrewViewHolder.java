package com.defaultapps.moviebase.ui.person;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastCrewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.moviePoster)
    public ImageView moviePoster;

    @BindView(R.id.personJob)
    public TextView personJob;

    public CastCrewViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
