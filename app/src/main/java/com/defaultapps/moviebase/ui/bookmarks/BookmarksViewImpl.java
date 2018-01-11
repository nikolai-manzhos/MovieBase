package com.defaultapps.moviebase.ui.bookmarks;


import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import easybind.Layout;
import easybind.bindings.BindNavigator;
import easybind.bindings.BindPresenter;

import com.airbnb.lottie.LottieAnimationView;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.di.FragmentContext;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.utils.ResUtils;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;

import static com.defaultapps.moviebase.ui.bookmarks.BookmarksContract.BookmarksPresenter;
import static com.defaultapps.moviebase.ui.bookmarks.BookmarksContract.BookmarksView;

@Layout(id = R.layout.fragment_bookmarks, name = "Bookmarks")
public class BookmarksViewImpl extends BaseFragment implements BookmarksView, OnMovieClickListener {

    @BindView(R.id.contentContainer)
    LinearLayout contentContainer;

    @BindView(R.id.favoriteRecyclerView)
    RecyclerView favoriteRecyclerView;

    @BindView(R.id.favoritesEmpty)
    FrameLayout favoritesEmpty;

    @BindView(R.id.no_user_container)
    ConstraintLayout noUserView;

    @BindView(R.id.user_animation)
    LottieAnimationView lottieAnimationView;

    @BindDimen(R.dimen.favorite_image_width)
    int columnWidthPx;

    @BindPresenter
    @Inject
    BookmarksPresenter presenter;

    @BindNavigator
    @FragmentContext
    @Inject
    Navigator navigator;

    @Inject
    ViewUtils viewUtils;

    @Inject
    ResUtils resUtils;

    @Inject
    @Nullable
    FavoritesAdapter favoritesAdapter;

    private ValueAnimator lottieValueAnimator;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initLottie();
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
        lottieValueAnimator.cancel();
        super.onDestroyView();
        if (favoritesAdapter != null) {
            favoritesAdapter.setOnMovieClickListener(null);
        }
    }

    @OnClick(R.id.no_user_container)
    void onLoginClick() {
        navigator.toSignInActivity();
    }

    @Override
    public void onMovieClick(int movieId) {
        navigator.toMovieActivity(movieId);
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

    private void initLottie() {
        lottieValueAnimator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(600L);
        lottieValueAnimator.addUpdateListener(valueAnimator -> lottieAnimationView.setProgress((Float) valueAnimator.getAnimatedValue()));
        lottieValueAnimator.start();
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
