package com.defaultapps.moviebase.ui.search;

import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.utils.ViewUtils;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class SearchViewTest extends BaseRobolectricTest {

    @Mock
    private SearchContract.SearchPresenter presenter;

    @Mock
    private SearchAdapter adapter;

    @Mock
    private ViewUtils viewUtils;

    @Mock
    private Navigator navigator;

    private SearchViewImpl searchView;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        searchView = new SearchViewImpl();
        searchView.presenter = presenter;
        searchView.searchAdapter = adapter;
        searchView.viewUtils = viewUtils;
        searchView.navigator = navigator;

        TestUtils.addFragmentToFragmentManager(searchView, activity);
    }

    @Test
    public void shouldStartFragment() {
        verify(fragmentComponent).inject(searchView);
    }

    @Test
    public void shouldOpenMovieActivityOnItemClick() {
        final int ANY_MOVIE_ID = 231321;
        searchView.onMovieClick(ANY_MOVIE_ID);
        verify(navigator).toMovieActivity(ANY_MOVIE_ID);
    }
}
