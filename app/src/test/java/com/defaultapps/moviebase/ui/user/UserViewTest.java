package com.defaultapps.moviebase.ui.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.base.BaseActivity;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.defaultapps.moviebase.ui.TestUtils.addFragmentToFragmentManager;
import static com.defaultapps.moviebase.ui.TestUtils.removeFragmentFromFragmentManager;
import static org.mockito.Mockito.verify;


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
}
