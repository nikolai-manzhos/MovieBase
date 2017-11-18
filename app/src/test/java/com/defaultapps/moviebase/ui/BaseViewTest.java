package com.defaultapps.moviebase.ui;

import android.support.annotation.CallSuper;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.FakeApplication;
import com.defaultapps.moviebase.di.component.ActivityComponent;
import com.defaultapps.moviebase.di.component.FragmentComponent;
import com.defaultapps.moviebase.ui.base.BaseActivity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SuppressWarnings("WeakerAccess")
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22, application = FakeApplication.class)
public abstract class BaseViewTest {

    protected FakeActivity activity;
    protected ActivityComponent activityComponent;
    protected FragmentComponent fragmentComponent;

    @CallSuper
    @Before
    public void setup() throws Exception {
        ActivityController<FakeActivity> controller = Robolectric.buildActivity(
                FakeActivity.class,
                null);
        activity = controller.get();

        activityComponent = mock(ActivityComponent.class);
        fragmentComponent = mock(FragmentComponent.class);

        controller.setup();

        Field activityComponentField = BaseActivity.class.getDeclaredField("activityComponent");
        activityComponentField.setAccessible(true);
        activityComponentField.set(activity, activityComponent);
        when(activityComponent.plusFragmentComponent()).thenReturn(fragmentComponent);

        MockitoAnnotations.initMocks(this);
    }
}
