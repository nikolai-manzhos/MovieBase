package com.defaultapps.moviebase.ui.user;


import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.TestUtils.removeFragmentFromFragmentManager;
import static com.defaultapps.moviebase.TestUtils.setupFakeAnalytics;
import static org.mockito.Mockito.verify;


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
        userView.redirectToAuth();

        verify(navigator).toAuth();
    }

    @Test
    public void shouldCloseActivityOnBackClick() {
        userView.getView().findViewById(R.id.backButton).performClick();

        verify(navigator).finishActivity();
    }
}
