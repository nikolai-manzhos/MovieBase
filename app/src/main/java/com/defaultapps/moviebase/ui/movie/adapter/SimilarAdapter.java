package com.defaultapps.moviebase.ui.movie.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.ui.movie.vh.SimilarViewHolder;

@PerActivity
public class SimilarAdapter extends RecyclerView.Adapter<SimilarViewHolder> {

    @Override
    public SimilarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SimilarViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
