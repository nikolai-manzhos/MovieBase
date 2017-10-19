package com.defaultapps.moviebase.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.api.Java6Assertions.assertThat;

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
        assertThat(Utils.convertDate(RAW_DATE)).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void convertDateFailure() throws Exception {
        assertThat(Utils.convertDate("March 2017")).isEqualTo("NaN");
    }

    @Test
    public void shouldSupplyProvidersList() {
        assertEquals(Utils.getProvidersList().size(), 2);
    }

}
