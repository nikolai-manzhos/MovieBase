package com.defaultapps.moviebase.ui.movie.vh;

import android.view.View;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;


public class CastViewHolder extends BaseViewHolder {

    @BindView(R.id.castPortrait)
    public CircleImageView castPortrait;

    @BindView(R.id.castName)
    public TextView castName;

    @BindView(R.id.castCharacter)
    public TextView castCharacter;

    public CastViewHolder(View v) {
        super(v);
    }
}
