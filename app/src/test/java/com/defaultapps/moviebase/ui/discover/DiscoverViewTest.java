package com.defaultapps.moviebase.ui.discover;

import android.content.ComponentName;


import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.genre.GenreActivity;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.ui.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.ui.TestUtils.removeFragmentFromFragmentManager;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

public class DiscoverViewTest extends BaseViewTest {

    @Mock
    private DiscoverAdapter adapter;

    @Mock
    private DiscoverPresenterImpl discoverPresenter;

    private DiscoverViewImpl discoverView;

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        MockitoAnnotations.initMocks(this);
        discoverView = new DiscoverViewImpl();
        discoverView.adapter = adapter;
        discoverView.presenter = discoverPresenter;

        addFragmentToFragmentManager(discoverView, activity, R.id.contentFrame);
    }

    @Test
    public void shouldStartFragment() {
        verify(fragmentComponent).inject(discoverView);
        verify(adapter).setListener(discoverView);
        verify(discoverPresenter).onAttach(discoverView);
        verify(discoverPresenter).requestData();
    }

    @Test
    public void shouldRedirectToGenreActivity() {
        ShadowActivity shadowActivity = shadowOf(activity);
        final String FAKE_GENRE_ID = "2313";
        final String FAKE_GENRE_NAME = "Movie";
        discoverView.onItemClick(FAKE_GENRE_ID, FAKE_GENRE_NAME);

        assertThat(shadowActivity.peekNextStartedActivity().getComponent())
                .isEqualTo(new ComponentName(activity, GenreActivity.class));
    }

    @Test
    public void shouldSetDataOnAdapter() {
        Genres response = new Genres();
        discoverView.showData(response);

        verify(adapter).setData(response);
    }

    @Test
    public void shouldDetachPresenterOnDestroyView() {
        removeFragmentFromFragmentManager(discoverView, activity);

        verify(discoverPresenter).onDetach();
    }
}
