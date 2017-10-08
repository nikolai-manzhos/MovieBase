package com.defaultapps.moviebase.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoadingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.loadmore_progress)
    public ProgressBar progressBar;

    @BindView(R.id.loadmore_retry)
    public ImageButton retryButton;

    @BindView(R.id.loadmore_errortxt)
    public TextView errorText;

    @BindView(R.id.loadmore_errorlayout)
    public LinearLayout errorLayout;

    public LoadingViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

}