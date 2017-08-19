package com.defaultapps.moviebase.ui.genre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.PaginationScrollListener;
import com.defaultapps.moviebase.utils.OnMovieClickListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class GenreViewImpl extends BaseFragment implements GenreContract.GenreView, OnMovieClickListener {

    @BindView(R.id.toolbarText)
    TextView toolbarText;

    @BindView(R.id.genreRecycler)
    RecyclerView genreRecycler;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    @Inject
    GenrePresenterImpl presenter;

    @Inject
    GenreAdapter adapter;

    private String genreId;
    private final int TOTAL_PAGES = 100;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_genre;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        presenter.onAttach(this);
        initRecyclerView();
        Bundle bundle = getArguments();
        genreId = bundle.getString(AppConstants.GENRE_ID);
        toolbarText.setText(bundle.getString(AppConstants.GENRE_NAME));
        if (savedInstanceState == null) {
            presenter.requestMovies(genreId, true);
        } else {
            presenter.requestMovies(genreId, false);
        }
        adapter.setOnMovieSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        adapter.setOnMovieSelectedListener(null);
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.errorButton)
    void onErrorClick() {
        presenter.requestMovies(genreId, true);
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        getActivity().startActivity(intent);
    }

    @Override
    public void showMovies(MoviesResponse movies) {
        adapter.setData(movies.getResults());
        Log.d("GenreView", String.valueOf(movies.getResults().size()));
    }

    @Override
    public void showMoreMovies(MoviesResponse movies) {
        adapter.addData(movies.getResults());
        genreRecycler.post(() -> adapter.notifyDataSetChanged()); // fix IllegalStateException
        isLoading = false;
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        genreRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        genreRecycler.setLayoutManager(layoutManager);
        PaginationScrollListener scrollListener = new PaginationScrollListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                presenter.requestMoreMovies(genreId);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }
        };
        genreRecycler.addOnScrollListener(scrollListener);

    }
}
