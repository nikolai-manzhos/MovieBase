package com.defaultapps.moviebase.ui.bookmarks;

import android.content.ComponentName;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.movie.MovieActivity;
import com.defaultapps.moviebase.utils.ViewUtils;
import com.firebase.ui.auth.KickoffActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.ui.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.ui.TestUtils.removeFragmentFromFragmentManager;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


public class BookmarksViewTest extends BaseViewTest {

    @Mock
    private BookmarksPresenterImpl presenter;

    @Mock
    private FavoritesAdapter favoritesAdapter;

    private BookmarksViewImpl bookmarksView;

    @Before
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        bookmarksView = new BookmarksViewImpl();
        bookmarksView.presenter = presenter;
        bookmarksView.favoritesAdapter = favoritesAdapter;

        addFragmentToFragmentManager(bookmarksView, activity);
    }

    @Test
    public void shouldStartFragment() {
        bookmarksView.favoritesAdapter = favoritesAdapter;
        verify(fragmentComponent).inject(bookmarksView);
        verify(presenter).onAttach(bookmarksView);
        verify(favoritesAdapter).setOnMovieClickListener(bookmarksView);
    }

    @Test
    public void shouldStartFragmentWithNullAdapter() {
        bookmarksView.favoritesAdapter = null;
        bookmarksView.onViewCreated(bookmarksView.getView(), null);
        verify(presenter).displayNoUserView();
    }

    @Test
    public void shouldOpenActivityOnLoginBtnClick() {
        ShadowActivity shadowActivity = shadowOf(activity);
        assert bookmarksView.getView() != null;
        bookmarksView.getView().findViewById(R.id.bookmarks_login_btn).performClick();

        assertEquals(shadowActivity.peekNextStartedActivityForResult().intent.getComponent(),
                new ComponentName(activity, KickoffActivity.class));
    }

    @Test
    public void shouldOpenMovieActivityOnFavoriteClick() {
        final int FAKE_MOVIE_ID = 1337;
        ShadowActivity shadowActivity = shadowOf(activity);
        bookmarksView.onMovieClick(FAKE_MOVIE_ID);

        assertEquals(shadowActivity.peekNextStartedActivity().getComponent(),
                new ComponentName(activity, MovieActivity.class));
    }

    @Test
    public void shouldDisplayNoUserMessage() {
        ConstraintLayout noUserViewMock = mock(ConstraintLayout.class);
        bookmarksView.noUserView = noUserViewMock;
        bookmarksView.showNoUserMessage();

        verify(noUserViewMock).setVisibility(View.VISIBLE);
    }

    @Test
    public void shouldHideNoUserView() {
        ConstraintLayout noUserViewMock = mock(ConstraintLayout.class);
        bookmarksView.noUserView = noUserViewMock;
        bookmarksView.hideNoUserMessage();

        verify(noUserViewMock).setVisibility(View.GONE);
    }

    @Test
    public void shouldPerformCleanUpOnDestroyView() {
        removeFragmentFromFragmentManager(bookmarksView, activity);

        verify(presenter).onDetach();
        verify(favoritesAdapter).setOnMovieClickListener(null);
    }

    @Test
    public void shouldStopListeningOnStop() {
        bookmarksView.onStop();

        verify(favoritesAdapter).stopListening();
    }

    @Test
    public void shouldStartListeningOnStart() {
        verify(favoritesAdapter).startListening();
    }

    @Test
    public void shouldNotStopListeningOnNullReference() {
        bookmarksView.favoritesAdapter = null;
        bookmarksView.onStop();

        verify(favoritesAdapter, never()).stopListening();
    }

    @Test
    public void shouldNotStartListeningOnNullReference() {
        reset(favoritesAdapter);
        bookmarksView.favoritesAdapter = null;
        bookmarksView.onStart();

        verify(favoritesAdapter, never()).startListening();
    }

    @Test
    public void shouldNotCleanAdapterOnNullReference() {
        bookmarksView.favoritesAdapter = null;
        removeFragmentFromFragmentManager(bookmarksView, activity);

        verify(presenter).onDetach();
        verify(favoritesAdapter, never()).setOnMovieClickListener(null);
    }

    @Test
    public void shouldDisplaySnackBarOnError() {
        bookmarksView.viewUtils = mock(ViewUtils.class);
        bookmarksView.displayErrorMessage();

        verify(bookmarksView.viewUtils).showSnackbar(bookmarksView.contentContainer,
                RuntimeEnvironment.application.getString(R.string.bookmarks_delete_error));
    }

}
