package com.defaultapps.moviebase.ui.movie.vh;

import android.view.View;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class CrewViewHolder extends BaseViewHolder {

    @BindView(R.id.crewPortrait)
    public CircleImageView crewPortrait;

    @BindView(R.id.crewName)
    public TextView crewName;

    @BindView(R.id.crewJob)
    public TextView crewJob;

    public CrewViewHolder(View v) {
        super(v);
    }
}
