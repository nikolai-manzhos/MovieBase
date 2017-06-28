package com.defaultapps.moviebase.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.Result;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerActivity;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.defaultapps.moviebase.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Result> movies;
    private Context context;
    private OnMovieClickListener listener;

    @Inject
    public SearchAdapter(@ActivityContext Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.searchContainer)
        RelativeLayout searchContainer;

        @BindView(R.id.moviePoster)
        ImageView moviePoster;

        @BindView(R.id.movieTitle)
        TextView movieTitle;

        @BindView(R.id.movieDate)
        TextView movieDate;

        SearchViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        String posterPath = movies.get(adapterPosition).getPosterPath();
        String icon = "{md-today}";
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + posterPath)
                .fit()
                .centerCrop()
                .into(holder.moviePoster);
        holder.movieTitle.setText(movies.get(adapterPosition).getTitle());
        holder.movieDate.setText(String.format(("%1$s" + Utils.convertDate(movies.get(adapterPosition).getReleaseDate())), icon));
        holder.searchContainer.setOnClickListener(view -> listener.onMovieClick(movies.get(adapterPosition).getId()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setData(List<Result> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }
}
