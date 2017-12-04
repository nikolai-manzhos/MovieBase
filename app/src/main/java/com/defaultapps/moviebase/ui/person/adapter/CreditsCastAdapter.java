package com.defaultapps.moviebase.ui.person.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.person.Cast;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.FragmentContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.defaultapps.moviebase.ui.person.CastCrewViewHolder;
import com.defaultapps.moviebase.utils.AppConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerFragment
public class CreditsCastAdapter extends RecyclerView.Adapter<CastCrewViewHolder> {

    private final Context context;
    private final DefaultNavigator defaultNavigator;
    private List<Cast> castCredits;

    @Inject
    CreditsCastAdapter(@ActivityContext Context context,
                       @FragmentContext DefaultNavigator defaultNavigator) {
        this.context = context;
        this.defaultNavigator = defaultNavigator;
        castCredits = new ArrayList<>();
    }


    @Override
    public CastCrewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastCrewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast_crew, parent, false));
    }

    @Override
    public void onBindViewHolder(CastCrewViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Cast cast = castCredits.get(adapterPosition);
        holder.personJob.setText(cast.getCharacter());
        holder.itemView.setOnClickListener(view -> defaultNavigator.toMovieActivity(cast.getId()));
        Picasso
                .with(context)
                .load(AppConstants.POSTER_BASE_URL + cast.getPosterPath())
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return castCredits.size();
    }

    public void setData(List<Cast> castCredits) {
        this.castCredits.clear();
        this.castCredits.addAll(castCredits);
        notifyDataSetChanged();
    }


}
