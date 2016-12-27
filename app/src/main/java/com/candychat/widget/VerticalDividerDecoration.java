package com.candychat.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by ZN_mager on 24/3/2016.
 */
public class VerticalDividerDecoration extends RecyclerView.ItemDecoration {
    private int bottom;
    private int right;
    private int left;
    private int top;

    public VerticalDividerDecoration(int top, int bottom, int left, int right) {
        this.bottom = bottom;
        this.top = top;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

        outRect.right = right;
        outRect.top = top;
        outRect.left = left;
        outRect.bottom = bottom;

    }

    public int getDividerSize() {
        return bottom;
    }

}
