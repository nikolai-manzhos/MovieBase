package com.defaultapps.moviebase.ui.bookmarks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;

import javax.inject.Inject;

import butterknife.BindView;

public class BookmarksViewImpl extends BaseFragment implements BookmarksContract.BookmarksView, OnMovieClickListener {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;

    @BindView(R.id.favoritesEmpty)
    FrameLayout favoritesEmpty;

    @Inject
    FavoritesAdapter favoritesAdapter;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_bookmarks;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        favoritesAdapter.setOnMovieClickListener(null);
        favoritesAdapter.cleanup();
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void displayErrorMessage() {
        showSnackbar(contentContainer, getString(R.string.user_delete_error));
    }

    @Override
    public void hideLoading() {}

    @Override
    public void showLoading() {}

    private void initRecyclerView() {
        favoriteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        favoriteRecyclerView.addItemDecoration(new SimpleItemDecorator(2,true));
        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();
        favoritesAdapter.setOnMovieClickListener(this);
    }
}
