package com.defaultapps.moviebase.ui.genre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.Result;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.di.scope.PerFragment;
import com.defaultapps.moviebase.ui.common.LoadingViewHolder;
import com.defaultapps.moviebase.utils.Utils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.defaultapps.moviebase.utils.listener.PaginationAdapterCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PerFragment
public class GenreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Result> items;
    private OnMovieClickListener listener;

    private final int GENRE = 0, LOADING = 1;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback callback;

    @Inject
    GenreAdapter(@ActivityContext Context context) {
        this.context = context;
        items = new ArrayList<>();
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
                bindGenreViewHolder((GenreViewHolder) holder, adapterPosition);
                break;
            case LOADING:
                bindLoadingViewHolder((LoadingViewHolder) holder);
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

    void addData(List<Result> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public void add(Result result) {
        this.items.add(result);
        notifyItemInserted(items.size() - 1);
    }

    void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Result());
    }

    void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() - 1;
        Result result = getItem(position);

        if (result != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    void showRetry(boolean show) {
        retryPageLoad = show;
        notifyItemChanged(items.size() - 1);
    }

    private Result getItem(int position) {
        return items.get(position);
    }

    void setOnMovieSelectedListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    void setPaginationCallback(PaginationAdapterCallback callback) {
        this.callback = callback;
    }

    private GenreViewHolder createGenreViewHolder(View view) {
        GenreViewHolder vh = new GenreViewHolder(view);
        vh.container.setOnClickListener(it -> listener.onMovieClick(items.get(vh.getAdapterPosition()).getId()));
        return vh;
    }

    private void bindGenreViewHolder(GenreViewHolder vh, int aPosition) {
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
        LoadingViewHolder vh = new LoadingViewHolder(view);
        vh.retryButton.setOnClickListener(it -> {
            showRetry(false);
            callback.retryPageLoad();
        });
        vh.errorLayout.setOnClickListener(it -> {
            showRetry(false);
            callback.retryPageLoad();
        });
        return vh;
    }

    private void bindLoadingViewHolder(LoadingViewHolder vh) {
        if (retryPageLoad) {
            vh.errorLayout.setVisibility(View.VISIBLE);
            vh.progressBar.setVisibility(View.GONE);

        } else {
            vh.errorLayout.setVisibility(View.GONE);
            vh.progressBar.setVisibility(View.VISIBLE);
        }
    }

    private String resolveRatingIcon(double rating) {
        if (rating <= 5.0) {
            return "{md-thumbs-up-down 24dp #17BD52}";
        } else {
            return "{md-thumb-up 24dp #17BD52}";
        }
    }
}
