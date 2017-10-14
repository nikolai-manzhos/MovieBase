package com.defaultapps.moviebase.ui.bookmarks;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.base.BaseActivity;
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
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


public class BookmarksViewTest extends BaseViewTest {

    @Mock
    private BookmarksPresenterImpl presenter;

    @Mock
    private FavoritesAdapter favoritesAdapter;

    private BookmarksViewImpl bookmarksView;
    @IdRes private static final int container = R.id.contentFrame;

    @NonNull
    @Override
    protected Class provideActivityClass() {
        return BaseActivity.class;
    }

    @Nullable
    @Override
    protected Intent provideActivityIntent() {
        return null;
    }

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_main;
    }

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        MockitoAnnotations.initMocks(this);
        bookmarksView = new BookmarksViewImpl();
        bookmarksView.presenter = presenter;
        bookmarksView.favoritesAdapter = favoritesAdapter;

        addFragmentToFragmentManager(bookmarksView, activity, container);
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

        assertThat(shadowActivity.peekNextStartedActivityForResult().intent.getComponent()).isEqualTo(new ComponentName(activity, KickoffActivity.class));
    }

    @Test
    public void shouldOpenMovieActivityOnFavoriteClick() {
        final int FAKE_MOVIE_ID = 1337;
        ShadowActivity shadowActivity = shadowOf(activity);
        bookmarksView.onMovieClick(FAKE_MOVIE_ID);

        assertThat(shadowActivity.peekNextStartedActivity().getComponent()).isEqualTo(new ComponentName(activity, MovieActivity.class));
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
        verify(favoritesAdapter).cleanup();
    }

    @Test
    public void shouldNotCleanAdapteOnNullReference() {
        bookmarksView.favoritesAdapter = null;

        verify(presenter).onDetach();
        verify(favoritesAdapter, never()).setOnMovieClickListener(null);
        verify(favoritesAdapter, never()).cleanup();
    }

    @Test
    public void shouldDisplaySnackBarOnError() {
        bookmarksView.viewUtils = mock(ViewUtils.class);
        bookmarksView.displayErrorMessage();

        verify(bookmarksView.viewUtils).showSnackbar(bookmarksView.contentContainer,
                RuntimeEnvironment.application.getString(R.string.bookmarks_delete_error));
    }

}
