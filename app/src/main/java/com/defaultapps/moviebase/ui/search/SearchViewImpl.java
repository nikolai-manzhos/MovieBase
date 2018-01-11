package com.defaultapps.moviebase.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.OnClick;
import easybind.Layout;
import easybind.bindings.BindNavigator;
import easybind.bindings.BindPresenter;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.di.FragmentContext;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.ui.search.SearchContract.SearchPresenter;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.defaultapps.moviebase.utils.Utils;
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

@Layout(id = R.layout.fragment_search, name = "Search")
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

    @BindPresenter
    @Inject
    SearchPresenter presenter;

    @Inject
    SearchAdapter searchAdapter;

    @Inject
    ViewUtils viewUtils;

    @BindNavigator
    @FragmentContext
    @Inject
    Navigator navigator;

    private BaseActivity activity;

    private int TOTAL_PAGES = 1;
    private boolean isLoading;
    private boolean isLastPage;
    private String currentQuery;

    private boolean isRestored;

    private ViewPropertyAnimator appearSearchStart;
    private ViewPropertyAnimator disappearSearchStart;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
        }
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setOnBackPressedListener(this);
        isRestored = savedInstanceState != null;

        initSearchView();
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        if (appearSearchStart != null) appearSearchStart.cancel();
        if (disappearSearchStart != null) disappearSearchStart.cancel();
        super.onDestroyView();
        activity.setOnBackPressedListener(null);
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
        navigator.toMovieActivity(movieId);
    }

    @Override
    public void displaySearchResults(MoviesResponse moviesResponse) {
        searchAdapter.setData(moviesResponse.getResults());
        TOTAL_PAGES = moviesResponse.getTotalPages();
        searchAdapter.removeLoadingFooter();
        isLastPage = false;

        if (moviesResponse.getPage() < TOTAL_PAGES) searchAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void displayMoreSearchResults(MoviesResponse moviesResponse) {
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
        appearSearchStart = searchStartView.animate()
                .setDuration(100L)
                .alpha(0)
                .withEndAction(() -> searchStartView.setVisibility(View.GONE));
    }

    @Override
    public void showSearchStart() {
        disappearSearchStart = searchStartView.animate()
                .setDuration(100L)
                .alpha(1)
                .withStartAction(() -> searchStartView.setVisibility(View.VISIBLE));
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

    @OnClick(R.id.searchStartView)
    void onSearchStartClick() {
        searchView.showSearch();
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
