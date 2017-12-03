package com.defaultapps.moviebase.ui.search;

import android.content.ComponentName;

import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.ViewUtils;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

public class SearchViewTest extends BaseRobolectricTest {

    @Mock
    private SearchContract.SearchPresenter presenter;

    @Mock
    private SearchAdapter adapter;

    @Mock
    private ViewUtils viewUtils;

    private SearchViewImpl searchView;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        searchView = new SearchViewImpl();
        searchView.presenter = presenter;
        searchView.searchAdapter = adapter;
        searchView.viewUtils = viewUtils;

        TestUtils.addFragmentToFragmentManager(searchView, activity);
    }

    @Test
    public void shouldStartFragment() {
        verify(fragmentComponent).inject(searchView);
    }

    @Test
    public void shouldOpenMovieActivityOnItemClick() {
        final int ANY_MOVIE_ID = 231321;
        ShadowActivity shadowActivity = shadowOf(activity);
        searchView.onMovieClick(ANY_MOVIE_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, MovieActivity.class));
    }


}
