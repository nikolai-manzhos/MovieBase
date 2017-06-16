package com.defaultapps.moviebase.ui.bookmarks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookmarksViewImpl extends Fragment implements BookmarksContract.BookmarksView, OnMovieClickListener {

    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;

    @Inject
    FavoritesAdapter favoritesAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getActivityComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);

        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void hideLoading() {}

    @Override
    public void showLoading() {}

    private void initRecyclerView() {
        favoriteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        favoriteRecyclerView.addItemDecoration(new SimpleItemDecorator(10,true));
        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.setOnMovieClickListener(this);
    }
}
