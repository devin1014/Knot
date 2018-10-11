package com.android.smartlink.ui.widget.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * User: LIUWEI
 * Date: 2017-12-25
 * Time: 11:44
 */
public class MyGridLayoutManager extends GridLayoutManager
{
    public MyGridLayoutManager(Context context)
    {
        super(context, 2);

        setSpanSizeLookup(new SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if (position <= 1)
                {
                    return 2;
                }

                return 1;
            }
        });
    }
}
