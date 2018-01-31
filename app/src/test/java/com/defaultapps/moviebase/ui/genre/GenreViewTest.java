package com.defaultapps.moviebase.ui.genre;

import android.os.Bundle;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.ui.common.MoviesAdapter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.TestUtils.removeFragmentFromFragmentManager;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;


public class GenreViewTest extends BaseRobolectricTest {

    private static final String FAKE_GENRE_ID = "213542";
    private static final String FAKE_GENRE_NAME = "Action";
    private static final int FAKE_MOVIE_ID = 21423;

    private GenreViewImpl genreView;

    @Mock
    private GenrePresenterImpl presenter;

    @Mock
    private MoviesAdapter adapter;

    @Mock
    private Navigator navigator;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        genreView = GenreViewImpl.newInstance(FAKE_GENRE_ID, FAKE_GENRE_NAME);
        genreView.presenter = presenter;
        genreView.adapter = adapter;
        genreView.navigator = navigator;

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
        verify(adapter).setOnMovieClickListener(null);
    }

    @Test
    public void shouldStartMovieActivityOnMovieClick() {
        genreView.onMovieClick(FAKE_MOVIE_ID);
        verify(navigator).toMovieActivity(FAKE_MOVIE_ID);
    }

    @Test
    public void shouldFinishActivityOnBackBtnClick() {
        genreView.onBackIconClick();
        verify(navigator).finishActivity();
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
