package com.defaultapps.moviebase.ui.movie;


import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.movie.adapter.CastAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.CrewAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.SimilarAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.VideosAdapter;
import com.defaultapps.moviebase.utils.ViewUtils;

import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.verify;

public class MovieViewTest extends BaseRobolectricTest {

    @Mock
    private MovieContract.MoviePresenter presenter;

    @Mock
    private VideosAdapter videosAdapter;

    @Mock
    private CastAdapter castAdapter;

    @Mock
    private CrewAdapter crewAdapter;

    @Mock
    private SimilarAdapter similarAdapter;

    @Mock
    private ViewUtils viewUtils;

    @Mock
    private MovieContract.MovieNavigator movieNavigator;

    private MovieViewImpl movieView;

    private static final int ANY_MOVIE_ID = 21344;

    @Override
    public void setup() throws Exception {
        super.setup();
        movieView = MovieViewImpl.newInstance(ANY_MOVIE_ID);
        movieView.presenter = presenter;
        movieView.videosAdapter = videosAdapter;
        movieView.castAdapter = castAdapter;
        movieView.crewAdapter = crewAdapter;
        movieView.similarAdapter = similarAdapter;
        movieView.viewUtils = viewUtils;
        movieView.navigator = movieNavigator;


        TestUtils.setupFakeAnalytics(movieView);
        TestUtils.addFragmentToFragmentManager(movieView, activity);
    }

    @Test
    public void shouldStartFragment() {
        verify(fragmentComponent).inject(movieView);
        verify(presenter).requestMovieInfo(ANY_MOVIE_ID, false);
        verify(presenter).requestFavoriteStatus(ANY_MOVIE_ID);
    }

    @Test
    public void shouldCallPresenterOnFABClick() throws Exception {
        MovieDetailResponse response = random(MovieDetailResponse.class);
        Field responseField = MovieViewImpl.class.getDeclaredField("movieInfo");
        responseField.setAccessible(true);
        responseField.set(movieView, response);
        responseField.setAccessible(false);
        assert movieView.getView() != null;

        movieView.getView().findViewById(R.id.favoriteFab).performClick();

        verify(presenter).addOrRemoveFromFavorites(response.getId(), response.getPosterPath());
    }

    @Test
    public void shouldOpenMovieActivityOnSimilarClick() {
        final int ANY_SIMILAR_MOVIE = 2313333;

        movieView.onMovieClick(ANY_SIMILAR_MOVIE);

        movieNavigator.toMovieActivity(ANY_SIMILAR_MOVIE);
    }

    @Test
    public void shouldOpenYouTubeActivityOnVideoClick() {
        final String ANY_VIDEO_PATH = "/sajd21b134";

        movieView.onVideoClick(ANY_VIDEO_PATH);

        verify(movieNavigator).toFullScreenVideoActivity(ANY_VIDEO_PATH);
    }

    @Test
    public void shouldCallPresenterOnErrorClick() {
        assert movieView.getView() != null;
        movieView.getView().findViewById(R.id.errorButton).performClick();

        verify(presenter).requestMovieInfo(ANY_MOVIE_ID, true);
    }

    @Test
    public void shouldOpenPersonActivityOnPersonClick() {
        final int ANY_PERSON_ID = 2141444;

        movieView.onPersonClick(ANY_PERSON_ID);

        movieNavigator.toPersonActivity(ANY_PERSON_ID);
    }

    @Test
    public void shouldCleanupOnDestroy() {
        TestUtils.removeFragmentFromFragmentManager(movieView, activity);

        verify(presenter).onDetach();
        verify(presenter).disposeUseCaseCalls();
    }

    @Test
    public void shouldDisplayTransactionError() {
        movieView.displayTransactionError();

        verify(viewUtils).showSnackbar(movieView.nestedScrollView, movieView.getString(R.string.movie_favorite_failure));
    }

    @Test
    public void displayLoginActivity() {
        movieView.displayLoginScreen();

        verify(movieNavigator).toLoginActivity();
    }

    @Test
    public void displayMovieInfo() {
        MovieDetailResponse response = random(MovieDetailResponse.class);
        movieView.displayMovieInfo(response);

        verify(videosAdapter).setData(response.getVideos().getVideoResults());
        verify(castAdapter).setData(response.getCredits().getCast());
        verify(crewAdapter).setData(response.getCredits().getCrew());
        verify(similarAdapter).setData(response.getSimilar().getResults());
    }
    //TODO: find the way to test MenuItem
}
