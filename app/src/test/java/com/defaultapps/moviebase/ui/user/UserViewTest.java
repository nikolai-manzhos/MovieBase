package com.defaultapps.moviebase.ui.user;

import android.app.Activity;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.ui.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.ui.TestUtils.removeFragmentFromFragmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;


public class UserViewTest extends BaseViewTest {

    @Mock
    private UserPresenterImpl presenter;

    private UserViewImpl userView;

    @Override
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        MockitoAnnotations.initMocks(this);
        userView = new UserViewImpl();
        userView.presenter = presenter;

        addFragmentToFragmentManager(userView, activity, R.id.contentFrame);
    }

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_main;
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
