package com.android.smartlink.ui.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * User: LIUWEI
 * Date: 2017-12-25
 * Time: 11:44
 */
public class ModuleGridLayoutManager extends GridLayoutManager
{
    public ModuleGridLayoutManager(Context context, final int spanCount)
    {
        super(context, spanCount);

        setOrientation(GridLayoutManager.VERTICAL);

        setSpanSizeLookup(new SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if (position == 0)
                {
                    return spanCount;
                }

                return 1;
            }
        });
    }
}
