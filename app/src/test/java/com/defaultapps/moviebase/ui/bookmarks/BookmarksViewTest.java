package com.defaultapps.moviebase.ui.bookmarks;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.defaultapps.moviebase.utils.ResUtils;
import com.defaultapps.moviebase.utils.ViewUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.RuntimeEnvironment;

import java.lang.reflect.Constructor;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.TestUtils.removeFragmentFromFragmentManager;
import static com.defaultapps.moviebase.TestUtils.setupFakeAnalytics;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;


public class BookmarksViewTest extends BaseRobolectricTest {

    @Mock
    private BookmarksPresenterImpl presenter;

    @Mock
    private FavoritesAdapter favoritesAdapter;

    @Mock
    private DefaultNavigator defaultNavigator;

    private BookmarksViewImpl bookmarksView;

    @Before
    public void setup() throws Exception {
        super.setup();
        Constructor<ResUtils> resUtilsConstructor = ResUtils.class.getDeclaredConstructor(Context.class);
        Constructor<ViewUtils> viewUtilsConstructor = ViewUtils.class.getDeclaredConstructor(Context.class);
        resUtilsConstructor.setAccessible(true);
        viewUtilsConstructor.setAccessible(true);
        ResUtils resUtils = resUtilsConstructor.newInstance(application);
        ViewUtils viewUtils = viewUtilsConstructor.newInstance(application);
        bookmarksView = new BookmarksViewImpl();
        bookmarksView.resUtils = resUtils;
        bookmarksView.viewUtils = viewUtils;
        bookmarksView.presenter = presenter;
        bookmarksView.favoritesAdapter = favoritesAdapter;
        bookmarksView.navigator = defaultNavigator;

        setupFakeAnalytics(bookmarksView);
        addFragmentToFragmentManager(bookmarksView, activity);
    }

    @Test
    public void shouldStartFragment() {
        bookmarksView.favoritesAdapter = favoritesAdapter;
        verify(fragmentComponent).inject(bookmarksView);
        verify(presenter).onAttach(bookmarksView);
    }

    @Test
    public void shouldStartFragmentWithNullAdapter() {
        bookmarksView.favoritesAdapter = null;
        bookmarksView.onViewCreated(bookmarksView.getView(), null);
        verify(presenter).displayNoUserView();
    }

    @Test
    public void shouldOpenActivityOnLoginBtnClick() {
        assert bookmarksView.getView() != null;
        bookmarksView.getView().findViewById(R.id.no_user_container).performClick();
        verify(defaultNavigator).toSignInActivity();
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
    public void shouldDisplaySnackBarOnError() {
        bookmarksView.viewUtils = mock(ViewUtils.class);
        bookmarksView.displayErrorMessage();

        verify(bookmarksView.viewUtils).showSnackbar(bookmarksView.contentContainer,
                RuntimeEnvironment.application.getString(R.string.bookmarks_delete_error));
    }

}
