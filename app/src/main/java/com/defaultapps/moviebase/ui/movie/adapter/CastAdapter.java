package com.defaultapps.moviebase.ui.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movie.Cast;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.ui.movie.vh.CastViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    private List<Cast> cast;
    private Context context;

    @Inject
    public CastAdapter(@ActivityContext Context context) {
        this.context = context;
        cast = new ArrayList<>();
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    public void setData(List<Cast> cast) {
        this.cast.clear();
        this.cast.addAll(cast);
        notifyDataSetChanged();
    }
}
