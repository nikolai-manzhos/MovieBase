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
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.ResUtils;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.defaultapps.moviebase.utils.Utils;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;
import com.firebase.ui.auth.AuthUI;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;

import static com.defaultapps.moviebase.ui.bookmarks.BookmarksContract.BookmarksPresenter;
import static com.defaultapps.moviebase.ui.bookmarks.BookmarksContract.BookmarksView;
import static com.defaultapps.moviebase.utils.AppConstants.RC_SIGN_IN;


public class BookmarksViewImpl extends BaseFragment implements BookmarksView, OnMovieClickListener {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;

    @BindView(R.id.favoritesEmpty)
    FrameLayout favoritesEmpty;

    @BindView(R.id.no_user_container)
    ConstraintLayout noUserView;

    @BindDimen(R.dimen.favorite_image_width)
    int columnWidthPx;

    @Inject
    BookmarksPresenter presenter;

    @Inject
    ViewUtils viewUtils;

    @Inject
    ResUtils resUtils;

    @Inject
    @Nullable
    FavoritesAdapter favoritesAdapter;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_bookmarks;
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
        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (favoritesAdapter != null) {
            favoritesAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (favoritesAdapter != null) {
            favoritesAdapter.stopListening();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (favoritesAdapter != null) {
            favoritesAdapter.setOnMovieClickListener(null);
        }
    }

    @OnClick(R.id.bookmarks_login_btn)
    void onLoginClick() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.DarkTheme)
                        .setLogo(R.mipmap.ic_launcher_round)
                        .setAvailableProviders(Utils.getProvidersList())
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void displayErrorMessage() {
        viewUtils.showSnackbar(contentContainer, getString(R.string.bookmarks_delete_error));
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
        int columnSizeDp = resUtils.convertPxToDp(columnWidthPx);
        favoriteRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                viewUtils.calculateNoOfColumns(columnSizeDp)));
        favoriteRecyclerView.addItemDecoration(new SimpleItemDecorator(2,true));
        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoritesAdapter.setOnMovieClickListener(this);
    }
}
