package com.defaultapps.moviebase.ui.movie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movie.Crew;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.movie.vh.CrewViewHolder;
import com.defaultapps.moviebase.utils.listener.OnPersonClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerFragment
public class CrewAdapter extends RecyclerView.Adapter<CrewViewHolder> {

    private Context context;
    private List<Crew> crewList;
    private OnPersonClickListener listener;

    @Inject
    CrewAdapter(@ActivityContext Context context) {
        this.context = context;
        crewList = new ArrayList<>();
    }

    @Override
    public CrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CrewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_crew, parent, false));
    }

    @Override
    public void onBindViewHolder(CrewViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + crewList.get(adapterPosition).getProfilePath())
                .placeholder(R.drawable.placeholder_human)
                .into(holder.crewPortrait);
        holder.crewName.setText(crewList.get(adapterPosition).getName());
        holder.crewJob.setText(crewList.get(adapterPosition).getJob());
        holder.crewPortrait.setOnClickListener(view -> listener.onPersonClick(crewList.get(adapterPosition).getId()));
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public void setOnPersonClickListener(OnPersonClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<Crew> crewList) {
        this.crewList.clear();
        this.crewList.addAll(crewList);
        notifyDataSetChanged();
    }
}
