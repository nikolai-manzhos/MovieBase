package com.defaultapps.moviebase.ui.login;

import android.app.Activity;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;

import static com.defaultapps.moviebase.TestUtils.addFragmentToFragmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

public class LoginViewTest extends BaseViewTest {

    private LoginViewImpl loginView;

    @Override
    public void setup() throws Exception {
        super.setup();
        loginView = new LoginViewImpl();

        addFragmentToFragmentManager(loginView, activity);
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
