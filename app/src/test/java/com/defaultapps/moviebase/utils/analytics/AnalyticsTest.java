package com.defaultapps.moviebase.utils.analytics;

import android.os.Bundle;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AnalyticsTest extends BaseRobolectricTest {

    private static final String ANY_SCREEN_NAME = "Main";

    private Analytics analytics;
    private FirebaseAnalytics firebaseAnalytics;

    @Before
    public void setup() {
        firebaseAnalytics = mock(FirebaseAnalytics.class);
        analytics = new AnalyticsImpl(firebaseAnalytics);
    }

    @Test
    public void shouldSendAnalyticsEvent() {
        analytics.sendScreenSelect(ANY_SCREEN_NAME);

        verify(firebaseAnalytics).logEvent(eq(FirebaseAnalytics.Event.SELECT_CONTENT), any(Bundle.class));
    }
}
