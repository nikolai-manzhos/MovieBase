package com.defaultapps.moviebase.ui.movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.movie.adapter.CastAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.CrewAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.SimilarAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.VideosAdapter;
import com.defaultapps.moviebase.ui.person.PersonActivity;
import com.defaultapps.moviebase.utils.AppConstants;
import com.defaultapps.moviebase.utils.OnMovieClickListener;
import com.defaultapps.moviebase.utils.OnPersonClickListener;
import com.defaultapps.moviebase.utils.SimpleItemDecorator;
import com.defaultapps.moviebase.utils.Utils;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;
import com.thefinestartist.ytpa.YouTubePlayerActivity;
import com.thefinestartist.ytpa.enums.Orientation;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MovieViewImpl extends BaseFragment implements MovieContract.MovieView, OnMovieClickListener, VideosAdapter.OnVideoClickListener, OnPersonClickListener {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.errorTextView)
    TextView errorText;

    @BindView(R.id.errorButton)
    Button errorButton;

    @BindView(R.id.toolbarContainer)
    AppBarLayout toolbarContainer;

    @BindView(R.id.mainContent)
    NestedScrollView nestedScrollView;

    @BindView(R.id.contentContainer)
    RelativeLayout contentContainer;

    @BindView(R.id.image)
    ImageView imageBackdrop;

    @BindView(R.id.imagePoster)
    ImageView imagePoster;

    @BindView(R.id.movieTitle)
    TextView movieTitle;

    @BindView(R.id.releaseDate)
    IconTextView releaseDate;

    @BindView(R.id.movieOverview)
    TextView movieOverview;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.favoriteFab)
    FloatingActionButton favoriteFab;

    @BindView(R.id.videosRecyclerView)
    RecyclerView videosRecyclerView;

    @BindView(R.id.castRecyclerView)
    RecyclerView castRecyclerView;

    @BindView(R.id.crewRecyclerView)
    RecyclerView crewRecyclerView;

    @BindView(R.id.similarRecyclerView)
    RecyclerView similarRecyclerView;

    @Inject
    MoviePresenterImpl presenter;

    @Inject
    VideosAdapter videosAdapter;

    @Inject
    CastAdapter castAdapter;

    @Inject
    CrewAdapter crewAdapter;

    @Inject
    SimilarAdapter similarAdapter;

    private Unbinder unbinder;
    private int movieId;
    private MovieInfoResponse movieInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((MovieActivity) getActivity()).getActivityComponent().inject(this);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        initFAB();
        initRecyclerViews();

        presenter.onAttach(this);
        movieId = getArguments().getInt(AppConstants.MOVIE_ID);
        presenter.requestMovieInfo(movieId, false);
        presenter.requestFavoriteStatus(movieId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
        similarAdapter.setOnMovieClickListener(null);
        videosAdapter.setOnVideoClickListener(null);
    }

    @OnClick(R.id.errorButton)
    void onErrorClick() {
        presenter.requestMovieInfo(movieId, true);
    }

    @OnClick(R.id.favoriteFab)
    void onFavoriteClick() {
        presenter.addOrRemoveFromFavorites(movieInfo.getId(), movieInfo.getPosterPath());
    }

    @Override
    public void onMovieClick(int movieId) {
        Intent intent = new Intent(getActivity(), MovieActivity.class).putExtra(AppConstants.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void onVideoClick(String videoPath) {
        Intent intent = new Intent(getActivity(), YouTubePlayerActivity.class);
        intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, videoPath);
        intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, Orientation.ONLY_LANDSCAPE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onPersonClick(int personId) {
        Intent intent = new Intent(getContext(), PersonActivity.class);
        intent.putExtra(AppConstants.PERSON_ID, personId);
        startActivity(intent);
    }

    @Override
    public void displayTransactionError() {
        showSnackbar(nestedScrollView, getString(R.string.movie_favorite_failure));
    }

    @Override
    public void displayMovieInfo(MovieInfoResponse movieInfo) {
        this.movieInfo = movieInfo;
        Picasso
                .with(getContext().getApplicationContext())
                .load("http://image.tmdb.org/t/p//w1280" + movieInfo.getBackdropPath())
                .fit()
                .centerCrop()
                .into(imageBackdrop);
        Picasso
                .with(getContext().getApplicationContext())
                .load("https://image.tmdb.org/t/p/w300" + movieInfo.getPosterPath())
                .fit()
                .centerCrop()
                .into(imagePoster);
        movieTitle.setText(movieInfo.getTitle());
        releaseDate.append(" " + Utils.convertDate(movieInfo.getReleaseDate()));
        movieOverview.setText(movieInfo.getOverview());
        videosAdapter.setData(movieInfo.getVideos().getVideoResults());
        castAdapter.setData(movieInfo.getCredits().getCast());
        crewAdapter.setData(movieInfo.getCredits().getCrew());
        similarAdapter.setData(movieInfo.getSimilar().getResults());
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
    public void hideData() {
        toolbarContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.GONE);
        favoriteFab.setVisibility(View.GONE);
    }

    @Override
    public void showData() {
        toolbarContainer.setVisibility(View.VISIBLE);
        contentContainer.setVisibility(View.VISIBLE);
        favoriteFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.GONE);
        errorButton.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        errorText.setVisibility(View.VISIBLE);
        errorButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFabStatus(boolean isActive) {
        favoriteFab.setImageDrawable(new IconDrawable(getContext(),
                (isActive ? MaterialIcons.md_favorite : MaterialIcons.md_favorite_border))
                .colorRes(R.color.colorAccent)
        );
    }

    private void initFAB() {
        favoriteFab.setImageDrawable(
                new IconDrawable(getContext(), MaterialIcons.md_favorite_border)
                .colorRes(R.color.colorAccent));
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    favoriteFab.hide();
                } else {
                    favoriteFab.show();
                }
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        MovieActivity activity = (MovieActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> activity.finish());
    }

    private void initRecyclerViews() {
        SimpleItemDecorator horizontalDivider = new SimpleItemDecorator(30, true);
        //noinspection deprecation
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        videosRecyclerView.setAdapter(videosAdapter);
        videosRecyclerView.addItemDecoration(horizontalDivider);
        videosRecyclerView.setNestedScrollingEnabled(false);
        videosAdapter.setOnVideoClickListener(this);

        castRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        castRecyclerView.setAdapter(castAdapter);
        castRecyclerView.addItemDecoration(horizontalDivider);
        castRecyclerView.setNestedScrollingEnabled(false);
        castAdapter.setOnPersonClickListener(this);

        crewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        crewRecyclerView.setAdapter(crewAdapter);
        crewRecyclerView.addItemDecoration(horizontalDivider);
        crewRecyclerView.setNestedScrollingEnabled(false);
        crewAdapter.setOnPersonClickListener(this);

        similarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        similarRecyclerView.setAdapter(similarAdapter);
        similarRecyclerView.addItemDecoration(horizontalDivider);
        similarRecyclerView.setNestedScrollingEnabled(false);
        similarAdapter.setOnMovieClickListener(this);
    }
}
