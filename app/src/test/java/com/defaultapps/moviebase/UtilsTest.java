package com.defaultapps.moviebase;

import android.util.Log;

import com.defaultapps.moviebase.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Log.class})
public class UtilsTest {

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Log.class);
    }

    @SuppressWarnings("unchecked")
    @Test(expected = AssertionError.class )
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

}
