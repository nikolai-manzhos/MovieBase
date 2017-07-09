package com.defaultapps.moviebase.espresso;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;


public class ViewAssertion {
    private ViewAssertion() {
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static android.support.test.espresso.ViewAssertion recyclerViewShouldHaveItem() {
        return (view, noViewFoundException) -> {
            final RecyclerView recyclerView = (RecyclerView) view;
            final int actualCount = recyclerView.getAdapter().getItemCount();

            if (actualCount < 0) {
                throw new AssertionError("RecyclerView has zero items");
            }
        };
    }
}
