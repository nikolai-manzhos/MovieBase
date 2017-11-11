package com.defaultapps.moviebase.ui.movie.vh;

import android.view.View;
import android.widget.ImageView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;


public class SimilarViewHolder extends BaseViewHolder{

    @BindView(R.id.moviePoster)
    public ImageView moviePoster;

    public SimilarViewHolder(View v) {
        super(v);
    }
}
