package com.defaultapps.moviebase.screen;

import android.support.annotation.NonNull;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.espresso.ViewAssertion;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


public class HomeScreen {

    @NonNull
    public HomeScreen hasToolbarTitle(@NonNull String title) {
        onView(allOf(withText(title), withParent(withId(R.id.toolbarText)))).check(matches(isDisplayed()));
        return this;
    }

    public HomeScreen hasUpcomingItems() {
        onView(withId(R.id.upcomingRecycler)).check(ViewAssertion.recyclerViewShouldHaveItem());
        return this;
    }
}
