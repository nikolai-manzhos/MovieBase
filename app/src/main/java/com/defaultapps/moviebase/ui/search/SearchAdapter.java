package com.defaultapps.moviebase.ui.search;

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
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int SEARCH = 0, LOADING = 1;

    private List<Result> movies;
    private Context context;
    private OnMovieClickListener listener;
    private PaginationAdapterCallback callback;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    @Inject
    SearchAdapter(@ActivityContext Context context) {
        this.context = context;
        movies = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case SEARCH:
                View vSearch = inflater.inflate(R.layout.item_search, parent, false);
                viewHolder = createSearchViewHolder(vSearch);
                break;
            case LOADING:
                View vLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = createLoadingViewHolder(vLoading);
                break;
            default:
                throw new IllegalArgumentException("No matching item found.");
        }
        return viewHolder;
    }

    private RecyclerView.ViewHolder createLoadingViewHolder(View view) {
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

    private RecyclerView.ViewHolder createSearchViewHolder(View view) {
        SearchViewHolder vh = new SearchViewHolder(view);
        vh.searchContainer.setOnClickListener(it -> listener.onMovieClick(movies.get(vh.getAdapterPosition()).getId()));
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        switch (holder.getItemViewType()) {
            case SEARCH:
                bindSearchViewHolder((SearchViewHolder) holder, adapterPosition);
                break;
            case LOADING:
                bindLoadingViewHolder((LoadingViewHolder) holder);
                break;
            default:
                throw new IllegalArgumentException("No matching item found.");
        }
    }

    private void bindLoadingViewHolder(LoadingViewHolder holder) {
        if (retryPageLoad) {
            holder.errorLayout.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);

        } else {
            holder.errorLayout.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void bindSearchViewHolder(SearchViewHolder holder, int position) {
        String posterPath = movies.get(position).getPosterPath();
        String icon = "{md-today}";
        Picasso
                .with(context)
                .load("https://image.tmdb.org/t/p/w300" + posterPath)
                .fit()
                .centerCrop()
                .into(holder.moviePoster);
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.movieDate.setText(String.format(("%1$s" + Utils.convertDate(movies.get(position).getReleaseDate())), icon));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movies.size() - 1 && isLoadingAdded) ? LOADING : SEARCH;
    }

    public void setData(List<Result> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    void addData(List<Result> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
    }

    public void add(Result result) {
        this.movies.add(result);
        notifyItemInserted(movies.size() - 1);
    }

    void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Result());
    }

    void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movies.size() - 1;
        Result result = getItem(position);

        if (result != null) {
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Result getItem(int position) {
        return movies.get(position);
    }

    void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    void setPaginationCallback(PaginationAdapterCallback callback) {
        this.callback = callback;
    }

    void showRetry(boolean show) {
        retryPageLoad = show;
        notifyItemChanged(movies.size() - 1);
    }
}
