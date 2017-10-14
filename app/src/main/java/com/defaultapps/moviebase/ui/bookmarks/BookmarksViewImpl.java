package com.defaultapps.moviebase.ui.bookmarks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.defaultapps.moviebase.utils.Utils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.firebase.ui.auth.AuthUI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BookmarksViewImpl extends BaseFragment implements BookmarksContract.BookmarksView, OnMovieClickListener {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;

    @BindView(R.id.favoritesEmpty)
    FrameLayout favoritesEmpty;

    @BindView(R.id.no_user_container)
    ConstraintLayout noUserView;

    @Inject
    BookmarksPresenterImpl presenter;

    @Inject
    @Nullable
    FavoritesAdapter favoritesAdapter;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_bookmarks;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getFragmentComponent().inject(this);
        presenter.onAttach(this);
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        if (favoritesAdapter != null) {
            favoritesAdapter.setOnMovieClickListener(null);
            favoritesAdapter.cleanup();
        }
    }

    @OnClick(R.id.bookmarks_login_btn)
    void onLoginClick() {
        getActivity().startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.DarkTheme)
                        .setLogo(R.mipmap.ic_launcher_round)
                        .setProviders(Utils.getProvidersList())
                        .build(),
                MainActivity.RC_SIGN_IN);
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
    public void showNoUserMessage() {
        noUserView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoUserMessage() {
        noUserView.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        if (favoritesAdapter == null) {
            presenter.displayNoUserView();
            return;
        }
        favoriteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        favoriteRecyclerView.addItemDecoration(new SimpleItemDecorator(2,true));
        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.setOnMovieClickListener(this);
    }
}
