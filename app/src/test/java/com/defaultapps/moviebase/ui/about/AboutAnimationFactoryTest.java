package com.defaultapps.moviebase.ui.about;

import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AboutAnimationFactoryTest extends BaseRobolectricTest {

    private AboutAnimationFactory aboutAnimationFactory;

    @Override
    public void setup() throws Exception {
        super.setup();
        aboutAnimationFactory = new AboutAnimationFactory();
    }

    @Test
    public void shouldCreateRotateAnimation() {
        assertNotNull(aboutAnimationFactory.createRotateAnimation());
    }

    @Test
    public void shouldCreateTranslateAnimation() {
        assertNotNull(aboutAnimationFactory.createTranslateAnimation(0f));
    }

    @Test
    public void shouldCreateScaleAnimation() {
        assertNotNull(aboutAnimationFactory.createScaleAnimation());
    }
}
