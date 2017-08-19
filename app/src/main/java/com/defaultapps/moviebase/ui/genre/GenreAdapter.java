package com.defaultapps.moviebase.ui.genre;

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
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.defaultapps.moviebase.utils.Utils;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerFragment
public class GenreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Result> items;
    private OnMovieClickListener listener;

    private final int GENRE = 0, LOADING = 1;

    private boolean isLoadingAdded = false;

    @Inject
    GenreAdapter(@ActivityContext Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.genreContainer)
        RelativeLayout container;

        @BindView(R.id.moviePoster)
        ImageView poster;

        @BindView(R.id.movieTitle)
        TextView title;

        @BindView(R.id.movieDate)
        IconTextView movieDate;

        @BindView(R.id.movieRating)
        IconTextView movieRating;

        GenreViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case GENRE:
                View vGenre = inflater.inflate(R.layout.item_genre, parent, false);
                viewHolder = createGenreViewHolder(vGenre);
                break;
            case LOADING:
                View vLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = createLoadingViewHolder(vLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        switch (holder.getItemViewType()) {
            case GENRE:
                configureGenreViewHolder((GenreViewHolder) holder, adapterPosition);
                break;
            case LOADING:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() - 1 && isLoadingAdded) ? LOADING : GENRE;
    }

    public void setData(List<Result> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void addData(List<Result> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public void setOnMovieSelectedListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    private GenreViewHolder createGenreViewHolder(View view) {
        GenreViewHolder vh = new GenreViewHolder(view);
        vh.container.setOnClickListener(it -> listener.onMovieClick(items.get(vh.getAdapterPosition()).getId()));
        return vh;
    }

    private void configureGenreViewHolder(GenreViewHolder vh, int aPosition) {
        final String ICON = "{md-today 24dp}";
        final String ICON_VOTE = resolveRatingIcon(items.get(aPosition).getVoteAverage());
        String posterPath = items.get(aPosition).getPosterPath();
        vh.title.setText(items.get(aPosition).getTitle());
        vh.movieDate.setText(String.format(("%1$s" + " " + Utils.convertDate(items.get(aPosition).getReleaseDate())), ICON));
        vh.movieRating.setText(String.format(("%1$s" + " " + items.get(aPosition).getVoteAverage()), ICON_VOTE));
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + posterPath)
                .fit()
                .centerCrop()
                .into(vh.poster);
    }

    private LoadingViewHolder createLoadingViewHolder(View view) {
        return new LoadingViewHolder(view);
    }

    private String resolveRatingIcon(double rating) {
        if (rating <= 5.0) {
            return "{md-thumbs-up-down 24dp #17BD52}";
        } else {
            return "{md-thumb-up 24dp #17BD52}";
        }
    }
}
