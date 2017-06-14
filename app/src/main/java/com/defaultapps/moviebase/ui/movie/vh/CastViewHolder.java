package com.defaultapps.moviebase.ui.movie.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class CastViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.castPortrait)
    public CircleImageView castPortrait;

    @BindView(R.id.castName)
    public TextView castName;

    @BindView(R.id.castCharacter)
    public TextView castCharacter;

    public CastViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
