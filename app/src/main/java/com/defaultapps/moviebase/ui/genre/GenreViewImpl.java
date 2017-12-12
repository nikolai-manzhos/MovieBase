package com.defaultapps.moviebase.ui.genre;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.genre.GenreContract.GenrePresenter;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.Utils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.defaultapps.moviebase.utils.listener.PaginationAdapterCallback;
import com.defaultapps.moviebase.utils.listener.PaginationScrollListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class GenreViewImpl extends BaseFragment implements GenreContract.GenreView,
        OnMovieClickListener, PaginationAdapterCallback {

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
    GenrePresenter presenter;

    @Inject
    GenreAdapter adapter;

    private String genreId;
    private int TOTAL_PAGES = 1;
    private boolean isLoading;
    private boolean isLastPage;

    public static GenreViewImpl newInstance(String id, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.GENRE_ID, id);
        bundle.putString(AppConstants.GENRE_NAME, name);

        GenreViewImpl genreView = new GenreViewImpl();
        genreView.setArguments(bundle);
        return genreView;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_genre;
    }

    @Override
    protected MvpPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        Utils.checkNotNull(bundle);
        genreId = bundle.getString(AppConstants.GENRE_ID);
        toolbarText.setText(bundle.getString(AppConstants.GENRE_NAME));
        initRecyclerView();
        if (savedInstanceState == null) {
            presenter.requestMovies(genreId, true);
        } else {
            presenter.requestMovies(genreId, false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.setOnMovieSelectedListener(null);
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        assert getActivity() != null;
        getActivity().finish();
    }

    @OnClick(R.id.errorButton)
    void onErrorClick() {
        presenter.requestMovies(genreId, true);
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void showMovies(MoviesResponse movies) {
        adapter.setData(movies.getResults());
        TOTAL_PAGES = movies.getTotalPages();
        adapter.removeLoadingFooter();
        isLastPage = false;

        if (movies.getPage() < TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void showMoreMovies(MoviesResponse movies) {
        adapter.addData(movies.getResults());
        genreRecycler.post(() -> adapter.notifyDataSetChanged()); // hack to fix IllegalStateException
        adapter.removeLoadingFooter();
        isLoading = false;

        if (movies.getPage() < TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void showLoadMoreError() {
        adapter.showRetry(true);
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

    @Override
    public void retryPageLoad() {
        presenter.requestMoreMovies(genreId);
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
        adapter.setOnMovieSelectedListener(this);
        adapter.setPaginationCallback(this);
    }
}
