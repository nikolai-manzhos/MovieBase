package com.defaultapps.moviebase.ui.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.defaultapps.moviebase.data.models.responses.movie.Cast;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.ui.movie.vh.CastViewHolder;


public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    private Context context;

    public CastAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
