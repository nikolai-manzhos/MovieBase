package com.defaultapps.moviebase.ui.movie.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class CrewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.crewPortrait)
    public CircleImageView crewPortrait;

    @BindView(R.id.crewName)
    public TextView crewName;

    @BindView(R.id.crewJob)
    public TextView crewJob;

    public CrewViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
