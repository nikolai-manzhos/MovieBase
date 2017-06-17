package com.defaultapps.moviebase.ui.bookmarks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookmarksViewImpl extends BaseFragment implements BookmarksContract.BookmarksView, OnMovieClickListener, FavoritesAdapter.OnMovieLongClickListener {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;

    @Inject
    DatabaseReference dbReference;

    @Inject
    BookmarksPresenterImpl presenter;

    private Unbinder unbinder;
    private FavoritesAdapter favoritesAdapter;

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
        presenter.onAttach(this);

        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
        favoritesAdapter.setOnMovieClickListener(null);
        favoritesAdapter.setLongClickListener(null);
        favoritesAdapter.cleanup();
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void onLongClick(String key) {
        showAlertDialog(getString(R.string.bookmarks_alert_title), null,
                (alertDialog, which) -> {
                    if (which == AlertDialog.BUTTON_POSITIVE) {
                        presenter.removeItemFromFavorites(key);
                    } else if (which == AlertDialog.BUTTON_NEGATIVE) {
                        alertDialog.dismiss();
                    }
                });
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
        favoritesAdapter = new FavoritesAdapter(dbReference, getContext());
        favoriteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        favoriteRecyclerView.addItemDecoration(new SimpleItemDecorator(10,true));
        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.setOnMovieClickListener(this);
        favoritesAdapter.setLongClickListener(this);
    }
}
