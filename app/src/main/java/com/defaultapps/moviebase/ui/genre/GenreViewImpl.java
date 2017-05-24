package com.defaultapps.moviebase.ui.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.main.MainActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.joanzapata.iconify.widget.IconButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class GenreViewImpl extends BaseFragment implements GenreContract.GenreView {

    @BindView(R.id.toolbarText)
    TextView toolbarText;

    @BindView(R.id.genreRecycler)
    RecyclerView genreRecycler;

    @Inject
    GenrePresenterImpl presenter;

    @Inject
    GenreAdapter adapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        ((GenreActivity) getActivity()).getActivityComponent().inject(this);

        presenter.onAttach(this);
        initRecyclerView();
        Bundle bundle = getArguments();
        toolbarText.setText(bundle.getString(AppConstants.GENRE_NAME));
        if (savedInstanceState == null) {
            presenter.requestMovies(bundle.getString(AppConstants.GENRE_ID), true);
        } else {
            presenter.requestMovies(bundle.getString(AppConstants.GENRE_ID), false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @OnClick(R.id.backButton)
    void onBackIconClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void showMovies(MoviesResponse movies) {
        adapter.setData(movies);
        Log.d("GenreView", String.valueOf(movies.getResults().size()));
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    private void initRecyclerView() {
        genreRecycler.setAdapter(adapter);
        genreRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
