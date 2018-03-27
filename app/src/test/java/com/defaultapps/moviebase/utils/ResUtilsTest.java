package com.defaultapps.moviebase.utils;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseRobolectricTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static junit.framework.Assert.assertEquals;


public class ResUtilsTest extends BaseRobolectricTest {

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

    @Test
    public void shouldResolveIconId() {
        assertEquals(R.drawable.ic_action, resUtils.resolveGenreIconId(28));
        assertEquals(R.drawable.ic_adventure, resUtils.resolveGenreIconId(12));
        assertEquals(R.drawable.ic_animation, resUtils.resolveGenreIconId(16));
        assertEquals(R.drawable.ic_comedy, resUtils.resolveGenreIconId(35));
        assertEquals(R.drawable.ic_crime, resUtils.resolveGenreIconId(80));
        assertEquals(R.drawable.ic_documentary, resUtils.resolveGenreIconId(99));
        assertEquals(R.drawable.ic_drama, resUtils.resolveGenreIconId(18));
        assertEquals(R.drawable.ic_family, resUtils.resolveGenreIconId(10751));
        assertEquals(R.drawable.ic_fantasy, resUtils.resolveGenreIconId(14));
        assertEquals(R.drawable.ic_history, resUtils.resolveGenreIconId(36));
        assertEquals(R.drawable.ic_horror, resUtils.resolveGenreIconId(27));
        assertEquals(R.drawable.ic_music, resUtils.resolveGenreIconId(10402));
        assertEquals(R.drawable.ic_mystery, resUtils.resolveGenreIconId(9648));
        assertEquals(R.drawable.ic_romance, resUtils.resolveGenreIconId(10749));
        assertEquals(R.drawable.ic_science_fiction, resUtils.resolveGenreIconId(878));
        assertEquals(R.drawable.ic_tv_movie, resUtils.resolveGenreIconId(10770));
        assertEquals(R.drawable.ic_thriller, resUtils.resolveGenreIconId(53));
        assertEquals(R.drawable.ic_war, resUtils.resolveGenreIconId(10752));
        assertEquals(R.drawable.ic_western, resUtils.resolveGenreIconId(37));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentOnWrongGenreId() {
        resUtils.resolveGenreIconId(1337);
    }
}
