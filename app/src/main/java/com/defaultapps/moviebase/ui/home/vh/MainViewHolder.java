package com.defaultapps.moviebase.ui.home.vh;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 5/16/2017.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movieBackdrop)
    public ImageView backdrop;

    @BindView(R.id.movieRating)
    public TextView rating;

    public MainViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
