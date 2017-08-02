package com.defaultapps.moviebase.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.person.Crew;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.person.CastCrewViewHolder;
import com.defaultapps.moviebase.utils.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerFragment
public class CreditsCrewAdapter extends RecyclerView.Adapter<CastCrewViewHolder> {

    private Context context;
    private List<Crew> crewCredits;

    @Inject
    public CreditsCrewAdapter(@ActivityContext Context context) {
        this.context = context;
        crewCredits = new ArrayList<>();
    }

    @Override
    public CastCrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastCrewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast_crew, parent, false));
    }

    @Override
    public void onBindViewHolder(CastCrewViewHolder holder, int position) {
        int aPosition = holder.getAdapterPosition();
        holder.personJob.setText(crewCredits.get(aPosition).getJob());
        Picasso
                .with(context)
                .load(AppConstants.POSTER_BASE_URL + crewCredits.get(aPosition).getPosterPath())
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return crewCredits.size();
    }

    public void setData(List<Crew> crewCredits) {
        this.crewCredits.clear();
        this.crewCredits.addAll(crewCredits);
        notifyDataSetChanged();
    }
}
