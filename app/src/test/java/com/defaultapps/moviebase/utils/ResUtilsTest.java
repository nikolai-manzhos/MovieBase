package com.defaultapps.moviebase.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ResUtilsTest {

    private static final int PX = 90;
    private static final int DP = 60;

    private ResUtils resUtils;

    //Robolectric's default screen density is 240dpi(hdpi)
    @Before
    public void setup() {
        resUtils = new ResUtils(RuntimeEnvironment.application);
    }

    @Test
    public void shouldConvertPxToDp() {
        assertEquals(DP, resUtils.convertPxToDp(PX));
    }

    @Test
    public void shouldConvertDpToPx() {
        assertEquals(PX, resUtils.convertDpToPx(DP));
    }
}
