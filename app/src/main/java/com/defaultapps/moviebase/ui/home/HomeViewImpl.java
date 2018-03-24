package com.defaultapps.moviebase.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.di.FragmentContext;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.ui.home.HomeContract.HomePresenter;
import com.defaultapps.moviebase.ui.home.adapter.HomeMainAdapter;
import com.defaultapps.moviebase.ui.home.adapter.UpcomingAdapter;
import com.defaultapps.moviebase.ui.user.UserActivity;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.defaultapps.moviebase.utils.listener.OnMovieClickListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import easybind.Layout;
import easybind.bindings.BindNavigator;
import easybind.bindings.BindPresenter;

import static com.defaultapps.moviebase.utils.AppConstants.RC_LOGIN;

@Layout(id = R.layout.fragment_home, name = "Home")
public class HomeViewImpl extends BaseFragment
        implements HomeContract.HomeView, SwipeRefreshLayout.OnRefreshListener, OnMovieClickListener {

    @BindView(R.id.homeRecycler)
    RecyclerView homeRecycler;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.errorTextView)
    TextView errorTextView;

    @BindView(R.id.errorButton)
    Button errorButton;

    @BindPresenter
    @Inject
    HomePresenter presenter;

    @Inject
    HomeMainAdapter adapter;

    @Inject
    UpcomingAdapter upcomingAdapter;

    @Inject
    ViewUtils viewUtils;

    @BindNavigator
    @FragmentContext
    @Inject
    Navigator navigator;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter.requestMoviesData(false);
    }

    @Override
    public void onRefresh() {
        presenter.requestMoviesData(true);
    }

    @Override
    public void onMovieClick(int movieId) {
        navigator.toMovieActivity(movieId);
    }

    @Override
    public void receiveResults(List<MoviesResponse> results) {
        adapter.setData(results);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void displayErrorMessage() {
        if (adapter.getItemCount() == 0) {
             errorButton.setVisibility(View.VISIBLE);
             errorTextView.setVisibility(View.VISIBLE);
        } else {
            viewUtils.showSnackbar(swipeRefreshLayout, getString(R.string.snackbar_error));
        }
    }

    @Override
    public void hideErrorView() {
        errorTextView.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    @Override
    public void displayProfileScreen() {
        Intent intent = new Intent(getActivity(), UserActivity.class);
        getActivity().startActivityForResult(intent, RC_LOGIN);
    }

    @Override
    public boolean isRefreshing() {
        return swipeRefreshLayout.isRefreshing();
    }

    @OnClick(R.id.profileButton)
    void onProfileClick() {
        presenter.openProfileScreen();
    }

    @OnClick(R.id.errorButton)
    void onErrorClick() {
        presenter.requestMoviesData(true);
    }

    private void initRecyclerView() {
        homeRecycler.setAdapter(adapter);
        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setMovieClickListener(this);
        upcomingAdapter.setMovieClickListener(this);
    }
}
