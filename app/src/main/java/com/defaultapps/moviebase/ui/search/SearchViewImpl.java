package com.defaultapps.moviebase.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.defaultapps.moviebase.utils.listener.OnBackPressedListener;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.defaultapps.moviebase.utils.listener.PaginationAdapterCallback;
import com.defaultapps.moviebase.utils.listener.PaginationScrollListener;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

public class SearchViewImpl extends BaseFragment implements
        SearchContract.SearchView, OnBackPressedListener,
        OnMovieClickListener, PaginationAdapterCallback {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.searchRecyclerView)
    RecyclerView searchRecyclerView;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    @BindView(R.id.searchStartView)
    LinearLayout searchStartView;

    @BindView(R.id.searchEmptyView)
    LinearLayout searchViewEmpty;

    @Inject
    SearchPresenterImpl presenter;

    @Inject
    SearchAdapter searchAdapter;

    @Inject
    ViewUtils viewUtils;

    private MainActivity activity;

    private int TOTAL_PAGES = 1;
    private boolean isLoading;
    private boolean isLastPage;
    private String currentQuery;

    private boolean isRestored;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            activity = (MainActivity) context;
        }
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        getFragmentComponent().inject(this);
        presenter.onAttach(this);
        activity.setOnBackPressedListener(this);
        isRestored = savedInstanceState != null;

        activity.setSupportActionBar(toolbar);
        assert activity.getSupportActionBar() != null;
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        initSearchView();
        initRecyclerView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setIcon(new IconDrawable(getContext(), MaterialIcons.md_search)
        .actionBarSize()
        .colorRes(R.color.colorPrimaryText));
        searchView.setMenuItem(menuItem);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.setSupportActionBar(null);
        activity.setOnBackPressedListener(null);
        presenter.onDetach();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public boolean onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return false;
        }
        return true;
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void displaySearchResults(MoviesResponse moviesResponse) {
        Timber.e("displaySearch");
        searchAdapter.setData(moviesResponse.getResults());
        TOTAL_PAGES = moviesResponse.getTotalPages();
        isLastPage = false;

        if (moviesResponse.getPage() < TOTAL_PAGES) searchAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void displayMoreSearchResults(MoviesResponse moviesResponse) {
        Timber.e("Display more search");
        searchAdapter.addData(moviesResponse.getResults());
        searchRecyclerView.post(() -> searchAdapter.notifyDataSetChanged());
        searchAdapter.removeLoadingFooter();
        isLoading = false;

        if (moviesResponse.getPage() < TOTAL_PAGES) searchAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void showData() {
        contentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMoreError() {
        searchAdapter.showRetry(true);
    }

    @Override
    public void showLoadMoreError() {
        searchAdapter.showRetry(false);
    }

    @Override
    public void hideSearchStart() {
        searchStartView.setVisibility(View.GONE);
    }

    @Override
    public void showSearchStart() {
        searchStartView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideData() {
        contentContainer.setVisibility(View.GONE);
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
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        searchViewEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        searchViewEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void retryPageLoad() {
        presenter.requestMoreSearchResults(currentQuery);
    }

    private void initSearchView() {
        searchView.setCursorDrawable(R.drawable.cursor);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                presenter.onSearchViewOpen();
            }

            @Override
            public void onSearchViewClosed() {
                presenter.onSearchViewClose();
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            Handler handler = new Handler(Looper.getMainLooper());
            Runnable workRunnable;
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewUtils.hideSoftKeyboard(searchView);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handler.removeCallbacks(workRunnable);
                workRunnable = () -> sendQuery(newText);
                currentQuery = newText;
                if (TextUtils.getTrimmedLength(newText) > 0) {
                    handler.postDelayed(workRunnable, 500);
                }
                return true;
            }

            private void sendQuery(String newText) {
                if (isRestored) {
                    presenter.requestSearchResults(newText, false);
                    isRestored = false;
                } else {
                    presenter.requestSearchResults(newText, true);
                }
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerView.addItemDecoration(new SimpleItemDecorator(10));
        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                presenter.requestMoreSearchResults(currentQuery);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        searchAdapter.setOnMovieClickListener(this);
        searchAdapter.setPaginationCallback(this);
    }
}
