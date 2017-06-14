package com.defaultapps.moviebase.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

@SuppressWarnings("unused")
public class SimpleItemDecorator extends RecyclerView.ItemDecoration {

    private int space;
    private boolean isHorizontalLayout;
    public SimpleItemDecorator(int space) {
        this.space = space;
    }

    public SimpleItemDecorator(int space, boolean isHorizontalLayout) {
        this.space = space;
        this.isHorizontalLayout = isHorizontalLayout;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(isHorizontalLayout)
        {
            outRect.bottom=space;
            outRect.right=space;
            outRect.left=space;
            outRect.top=space;

        } else {
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = space;
            else
                outRect.top = 0;

        }
    }

}
