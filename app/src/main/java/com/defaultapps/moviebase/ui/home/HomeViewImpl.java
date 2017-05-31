package com.defaultapps.moviebase.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.home.adapter.HomeMainAdapter;
import com.defaultapps.moviebase.ui.home.adapter.OnMovieSelected;
import com.defaultapps.moviebase.ui.home.adapter.UpcomingAdapter;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.firebase.ui.auth.AuthUI;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeViewImpl extends BaseFragment implements HomeContract.HomeView, SwipeRefreshLayout.OnRefreshListener, OnMovieSelected {

    @BindView(R.id.homeRecycler)
    RecyclerView homeRecycler;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    HomePresenterImpl presenter;

    @Inject
    HomeMainAdapter adapter;

    @Inject
    UpcomingAdapter upcomingAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getActivityComponent().inject(this);

        presenter.onAttach(this);
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        if (savedInstanceState == null) {
            presenter.requestMoviesData(true);
        } else {
            presenter.requestMoviesData(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void onRefresh() {
        presenter.requestMoviesData(true);
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class);
        intent.putExtra(AppConstants.MOVIE_ID, movieId);
        getActivity().startActivity(intent);
    }

    @Override
    public void receiveResults(List<MoviesResponse> results) {
        adapter.setData(results);
        adapter.setMovieSelectedListener(this);
        upcomingAdapter.setMovieSelectedListener(this);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @OnClick(R.id.profileButton)
    void onProfileClick() {
        AuthUI.getInstance().signOut(getActivity());
    }

    private void initRecyclerView() {
        homeRecycler.setAdapter(adapter);
        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
