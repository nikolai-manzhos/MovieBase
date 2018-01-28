package com.defaultapps.moviebase.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.discover.DiscoverContract.DiscoverPresenter;
import com.defaultapps.moviebase.ui.genre.GenreActivity;
import com.defaultapps.moviebase.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import easybind.Layout;
import easybind.bindings.BindPresenter;

@Layout(id = R.layout.fragment_discover, name = "Discover")
public class DiscoverViewImpl extends BaseFragment implements DiscoverContract.DiscoverView, DiscoverAdapter.OnItemClickListener {

    @BindView(R.id.discoveryRecycler)
    RecyclerView recyclerView;

    @Inject
    DiscoverAdapter adapter;

    @BindPresenter
    @Inject
    DiscoverPresenter presenter;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.setListener(this);
        initRecyclerView();
        presenter.requestData();
    }

    @Override
    public void onItemClick(String genreId, String genreName) {
        Intent intent = new Intent(getActivity(), GenreActivity.class);
        intent.putExtra(AppConstants.GENRE_ID, genreId);
        intent.putExtra(AppConstants.GENRE_NAME, genreName);
        startActivity(intent);
    }

    @Override
    public void showData(Genres genres) {
        adapter.setData(genres);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }
}
