package com.defaultapps.moviebase.ui.genre;

import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.TestUtils.removeFragmentFromFragmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


public class GenreViewTest extends BaseRobolectricTest {

    private static final String FAKE_GENRE_ID = "213542";
    private static final String FAKE_GENRE_NAME = "Action";
    private static final int FAKE_MOVIE_ID = 21423;

    private GenreViewImpl genreView;

    @Mock
    private GenrePresenterImpl presenter;

    @Mock
    private GenreAdapter adapter;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        genreView = GenreViewImpl.newInstance(FAKE_GENRE_ID, FAKE_GENRE_NAME);
        genreView.presenter = presenter;
        genreView.adapter = adapter;

        addFragmentToFragmentManager(genreView, activity);
    }

    @Test
    public void shouldStartFragment() {
        final boolean FORCE = true;
        verify(fragmentComponent).inject(genreView);
        verify(presenter).onAttach(genreView);
        verify(presenter).requestMovies(FAKE_GENRE_ID, FORCE);
        verify(presenter, never()).requestMovies(FAKE_GENRE_ID, false);
    }

    @Test
    public void shouldPerformCleanupOnDestroy() {
        removeFragmentFromFragmentManager(genreView, activity);

        verify(presenter).onDetach();
        verify(adapter).setOnMovieSelectedListener(null);
    }

    @Test
    public void shouldStartMovieActivityOnMovieClick() {
        ShadowActivity shadowActivity = shadowOf(activity);
        genreView.onMovieClick(FAKE_MOVIE_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getIntExtra(AppConstants.MOVIE_ID, 0),
                FAKE_MOVIE_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, MovieActivity.class));
    }

    @Test
    public void shouldFinishActivityOnBackBtnClick() {
        ShadowActivity shadowActivity = shadowOf(activity);
        assert genreView.getView() != null;
        genreView.getView().findViewById(R.id.backButton).performClick();

        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void shouldCallPresenterForCachedDataOnConfigChanges() {
        genreView.onViewCreated(genreView.getView(), new Bundle());

        verify(presenter).requestMovies(FAKE_GENRE_ID, false);
    }

    @Test
    public void shouldResendRequestOnErrorButtonClick() {
        reset(presenter);
        View view = genreView.getView();
        assert view != null;
        view.findViewById(R.id.errorButton).performClick();

        verify(presenter).requestMovies(FAKE_GENRE_ID, true);
    }
}
