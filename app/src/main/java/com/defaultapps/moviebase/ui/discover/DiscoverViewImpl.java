package com.defaultapps.moviebase.ui.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.genre.GenreActivity;
import com.defaultapps.moviebase.ui.genre.GenreViewImpl;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class DiscoverViewImpl extends BaseFragment implements DiscoverContract.DiscoverView, DiscoverAdapter.OnItemClickListener {

    @BindView(R.id.discoveryRecycler)
    RecyclerView recyclerView;

    @Inject
    DiscoverAdapter adapter;

    @Inject
    DiscoverPresenterImpl presenter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getActivityComponent().inject(this);
        adapter.setListener(this);
        presenter.onAttach(this);
        initRecyclerView();
        presenter.requestData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }
    
    @Override
    public void onItemClick(String genreId, String genreName) {
        Intent intent = new Intent(getActivity(), GenreActivity.class);
        intent.putExtra(AppConstants.GENRE_ID, genreId);
        intent.putExtra(AppConstants.GENRE_NAME, genreName);
        startActivity(intent);
    }

    @Override
    public void hideLoading() {}

    @Override
    public void showLoading() {}

    @Override
    public void showData(Genres genres) {
        adapter.setData(genres);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }
}
