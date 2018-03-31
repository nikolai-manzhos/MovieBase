package com.defaultapps.moviebase.ui.about;

import com.defaultapps.moviebase.TestUtils;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;
import com.defaultapps.moviebase.ui.base.Navigator;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

public class AboutViewTest extends BaseRobolectricTest {

    @Mock
    private Navigator<AboutViewImpl> navigator;

    private AboutViewImpl aboutView;

    @Override
    public void setup() throws Exception {
        super.setup();
        aboutView = new AboutViewImpl();
        aboutView.navigator = navigator;
        aboutView.animationFactory = new AboutAnimationFactory();

        TestUtils.setupFakeAnalytics(aboutView);
        TestUtils.addFragmentToFragmentManager(aboutView, activity);
    }

    @Test
    public void checkIfFragmentSuccessfullyStarted() {
        verify(fragmentComponent).inject(aboutView);
        verify(navigator).onAttach(aboutView);
    }

    @Test
    public void openLinkOnGithubIconClick() {
        aboutView.githubIcon.performClick();

        verify(navigator).openLink(anyString());
    }

}
