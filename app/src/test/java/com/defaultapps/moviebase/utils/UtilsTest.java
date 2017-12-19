package com.defaultapps.moviebase.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.Assert.assertEquals;

public class UtilsTest {

    @SuppressWarnings("unchecked")
    @Test(expected = InvocationTargetException.class)
    public void privateConstructorTest() throws Exception {
        Constructor<Utils> constructor= (Constructor<Utils>) Utils.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void convertDateTestSuccess() throws Exception {
        final String RAW_DATE = "2017-01-21";
        final String EXPECTED_RESULT = " January-21, 2017";
        assertEquals(Utils.convertDate(RAW_DATE), EXPECTED_RESULT);
    }

    @Test
    public void convertDateFailure() throws Exception {
        assertEquals(Utils.convertDate("March 2017"), "NaN");
    }

    @Test
    public void shouldSupplyProvidersList() {
        assertEquals(Utils.getProvidersList().size(), 2);
    }

    @Test
    public void shouldFormatIntNumber() {
        assertEquals(Utils.formatNumber(10000), "10,000");
    }

    @Test
    public void shouldFormatLongNumber() {
        assertEquals(Utils.formatNumber(100000L), "100,000");
    }
}
