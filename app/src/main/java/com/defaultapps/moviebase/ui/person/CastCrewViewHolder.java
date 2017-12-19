package com.defaultapps.moviebase.ui.person;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;

public class CastCrewViewHolder extends BaseViewHolder {

    @BindView(R.id.moviePoster)
    public ImageView moviePoster;

    @BindView(R.id.personJob)
    public TextView personJob;

    public CastCrewViewHolder(View v) {
        super(v);
    }
}
