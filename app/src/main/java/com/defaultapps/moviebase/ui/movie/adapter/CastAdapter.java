package com.defaultapps.moviebase.ui.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movie.Cast;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.ui.movie.vh.CastViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    private List<Cast> cast;
    private Context context;

    @Inject
    CastAdapter(@ActivityContext Context context) {
        this.context = context;
        cast = new ArrayList<>();
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + cast.get(adapterPosition).getProfilePath())
                .placeholder(R.drawable.placeholder_human)
                .into(holder.castPortrait);
        holder.castName.setText(cast.get(adapterPosition).getName());
        holder.castCharacter.setText(cast.get(adapterPosition).getCharacter());
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
