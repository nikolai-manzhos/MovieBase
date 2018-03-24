package com.defaultapps.moviebase.ui.user;

import android.app.Activity;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.TestUtils.removeFragmentFromFragmentManager;
import static com.defaultapps.moviebase.TestUtils.setupFakeAnalytics;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


public class UserViewTest extends BaseRobolectricTest {

    @Mock
    private UserPresenterImpl presenter;

    @Mock
    private UserContract.UserNavigator navigator;

    private UserViewImpl userView;

    @Override
    public void setup() throws Exception {
        super.setup();
        MockitoAnnotations.initMocks(this);
        userView = new UserViewImpl();
        userView.presenter = presenter;
        userView.userNavigator = navigator;

        setupFakeAnalytics(userView);
        addFragmentToFragmentManager(userView, activity);
    }

    @Test
    public void shouldStartFragment() {
        verify(fragmentComponent).inject(userView);
        verify(presenter).onAttach(userView);
        verify(presenter).checkUserStatus();
    }

    @Test
    public void shouldPerformCleanupOnDestroy() {
        removeFragmentFromFragmentManager(userView, activity);

        verify(presenter).onDetach();
    }

    @Test
    public void shouldRedirectToAuth() {
        ShadowActivity shadowActivity = shadowOf(activity);
        userView.redirectToAuth();

        assertEquals(Activity.RESULT_OK, shadowActivity.getResultCode());
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void shouldCloseActivityOnBackClick() {
        ShadowActivity shadowActivity = shadowOf(activity);
        assert userView.getView() != null;
        userView.getView().findViewById(R.id.backButton).performClick();

        assertTrue(shadowActivity.isFinishing());
    }
}
