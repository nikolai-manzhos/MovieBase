package com.defaultapps.moviebase.ui;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.di.component.ActivityComponent;
import com.defaultapps.moviebase.di.component.FragmentComponent;
import com.defaultapps.moviebase.ui.base.BaseActivity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SuppressWarnings("WeakerAccess")
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = FakeApplication.class)
public abstract class BaseViewTest {

    protected BaseActivity activity;
    protected ActivityComponent activityComponent;
    protected FragmentComponent fragmentComponent;

    @CallSuper
    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        @SuppressWarnings("unchecked")
        ActivityController<BaseActivity> controller = Robolectric.buildActivity(
                BaseActivity.class,
                null);
        activity = controller.get();
        activityComponent = mock(ActivityComponent.class);
        fragmentComponent = mock(FragmentComponent.class);

        Integer layout = provideLayoutId();
        if (layout != null) {
            activity.setContentView(layout);
        }

        controller.setup();

        Field activityComponentField = BaseActivity.class.getDeclaredField("activityComponent");
        activityComponentField.setAccessible(true);
        activityComponentField.set(activity, activityComponent);
        when(activityComponent.plusFragmentComponent()).thenReturn(fragmentComponent);
    }

    @LayoutRes
    protected abstract Integer provideLayoutId();
}
