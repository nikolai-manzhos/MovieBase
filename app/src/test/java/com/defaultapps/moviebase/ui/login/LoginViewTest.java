package com.defaultapps.moviebase.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.base.BaseActivity;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.ui.TestUtils.addFragmentToFragmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

public class LoginViewTest extends BaseViewTest {

    private LoginViewImpl loginView;

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        loginView = new LoginViewImpl();

        addFragmentToFragmentManager(loginView, activity, R.id.contentFrame);
    }

    @Test
    public void shouldFinishWithResultOk() {
        ShadowActivity shadowActivity = shadowOf(activity);
        assert loginView.getView() != null;
        loginView.getView().findViewById(R.id.login_sign).performClick();

        assertEquals(Activity.RESULT_OK, shadowActivity.getResultCode());
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void shouldFinishWithResultCancel() {
        ShadowActivity shadowActivity = shadowOf(activity);
        assert loginView.getView() != null;
        loginView.getView().findViewById(R.id.login_skip).performClick();

        assertEquals(Activity.RESULT_CANCELED, shadowActivity.getResultCode());
        assertTrue(shadowActivity.isFinishing());
    }
}
