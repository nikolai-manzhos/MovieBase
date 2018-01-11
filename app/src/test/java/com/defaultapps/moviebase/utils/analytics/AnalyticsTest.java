package com.defaultapps.moviebase.utils.analytics;


import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class AnalyticsTest {

    private Analytics analytics;
    private FirebaseAnalytics firebaseAnalytics;

    @Before
    public void setup() {
        firebaseAnalytics = mock(FirebaseAnalytics.class);
        analytics = new AnalyticsImpl(firebaseAnalytics);
    }

    @Test
    public void shouldSendAnalyticsEvent() {
        final String ANY_SCREEN_NAME = "Main";
        analytics.sendScreenSelect(ANY_SCREEN_NAME);

        verify(firebaseAnalytics).logEvent(eq(FirebaseAnalytics.Event.SELECT_CONTENT), any(Bundle.class));
    }
}
