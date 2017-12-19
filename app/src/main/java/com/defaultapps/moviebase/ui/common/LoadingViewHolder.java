package com.defaultapps.moviebase.ui.common;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseViewHolder;

import butterknife.BindView;


public class LoadingViewHolder extends BaseViewHolder {

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
    }

}