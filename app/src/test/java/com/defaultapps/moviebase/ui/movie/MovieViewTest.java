package com.defaultapps.moviebase.ui.movie;

import android.content.ComponentName;
import android.content.Intent;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.defaultapps.moviebase.ui.movie.adapter.CastAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.CrewAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.SimilarAdapter;
import com.defaultapps.moviebase.ui.movie.adapter.VideosAdapter;
import com.defaultapps.moviebase.ui.person.PersonActivity;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.thefinestartist.ytpa.YouTubePlayerActivity;
import com.thefinestartist.ytpa.enums.Orientation;

import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.shadows.ShadowActivity;

import java.lang.reflect.Field;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

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
    private DefaultNavigator defaultNavigator;

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
        movieView.navigator = defaultNavigator;

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
        MovieInfoResponse response = random(MovieInfoResponse.class);
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
        ShadowActivity shadowActivity = shadowOf(activity);

        movieView.onMovieClick(ANY_SIMILAR_MOVIE);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, MovieActivity.class));
    }

    @Test
    public void shouldOpenYouTubeActivityOnVideoClick() {
        final String ANY_VIDEO_PATH = "/sajd21b134";
        ShadowActivity shadowActivity = shadowOf(activity);

        movieView.onVideoClick(ANY_VIDEO_PATH);

        Intent intent = shadowActivity.peekNextStartedActivity();
        assertEquals(ANY_VIDEO_PATH,
                intent.getStringExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID));
        assertEquals(Orientation.ONLY_LANDSCAPE,
                intent.getSerializableExtra(YouTubePlayerActivity.EXTRA_ORIENTATION));
        assertEquals(Intent.FLAG_ACTIVITY_NEW_TASK,
                intent.getFlags());
        assertEquals(intent.getComponent(),
                new ComponentName(activity, YouTubePlayerActivity.class));
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
        ShadowActivity shadowActivity = shadowOf(activity);

        movieView.onPersonClick(ANY_PERSON_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, PersonActivity.class));
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

        verify(defaultNavigator).toLoginActivity();
    }

    @Test
    public void shouldPerformShareAction() throws Exception {
        MovieInfoResponse movieDataFake = random(MovieInfoResponse.class);
        Field movieInfoField = movieView.getClass().getDeclaredField("movieInfo");
        movieInfoField.setAccessible(true);
        movieInfoField.set(movieView, movieDataFake);

        ShadowActivity shadowActivity = shadowOf(activity);
        assert movieView.getView() != null;
        movieView.getView().findViewById(R.id.shareButton).performClick();

        assertEquals(shadowActivity.peekNextStartedActivity().getAction(),
                Intent.ACTION_SEND);
    }
}
