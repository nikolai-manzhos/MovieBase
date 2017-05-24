package com.defaultapps.moviebase.ui.genre;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.utils.AppConstants;

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

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    @Inject
    GenrePresenterImpl presenter;

    @Inject
    GenreAdapter adapter;

    private Unbinder unbinder;
    private String genreId;

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
        genreId = bundle.getString(AppConstants.GENRE_ID);
        toolbarText.setText(bundle.getString(AppConstants.GENRE_NAME));
        if (savedInstanceState == null) {
            presenter.requestMovies(genreId, true);
        } else {
            presenter.requestMovies(genreId, false);
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

    @OnClick(R.id.errorButton)
    void onErrorClick() {
        presenter.requestMovies(genreId, true);
    }

    @Override
    public void showMovies(MoviesResponse movies) {
        adapter.setData(movies);
        Log.d("GenreView", String.valueOf(movies.getResults().size()));
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        genreRecycler.setAdapter(adapter);
        genreRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
