package com.android.devin.core.ui.widget.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 15:46
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration
{
    private final int mOffsetX; //px

    private final int mOffsetY; //px

    public CommonItemDecoration(int offsetX, int offsetY)
    {
        mOffsetX = offsetX;

        mOffsetY = offsetY;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        if (mOffsetX > 0)
        {
            outRect.left = mOffsetX / 2;

            outRect.right = mOffsetX / 2;
        }

        if (mOffsetY > 0)
        {
            outRect.bottom = mOffsetY;
        }
    }
}

