package com.defaultapps.moviebase.ui.genre;

import android.content.ComponentName;
import android.os.Bundle;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.AppConstants;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.ui.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.ui.TestUtils.removeFragmentFromFragmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


public class GenreViewTest extends BaseViewTest {

    private static final String FAKE_GENRE_ID = "213542";
    private static final String FAKE_GENRE_NAME = "Action";
    private static final int FAKE_MOVIE_ID = 21423;

    private GenreViewImpl genreView;

    @Mock
    private GenrePresenterImpl presenter;

    @Mock
    private GenreAdapter adapter;

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_genre;
    }

    @Override
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        MockitoAnnotations.initMocks(this);
        genreView = new GenreViewImpl();
        genreView.presenter = presenter;
        genreView.adapter = adapter;

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.GENRE_ID, FAKE_GENRE_ID);
        bundle.putString(AppConstants.GENRE_NAME, FAKE_GENRE_NAME);
        genreView.setArguments(bundle);

        addFragmentToFragmentManager(genreView, activity, R.id.contentFrame);
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
}
