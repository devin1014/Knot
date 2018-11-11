package com.android.smartlink.util.ui;

import android.content.Context;

import com.android.smartlink.ui.widget.ScaleCircleNavigator;

public class MagicIndicatorHelper
{
    public static ScaleCircleNavigator newScaleCircleNavigator(Context context, int navigatorCount, int[] navColors, int[] navRadius)
    {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(context);
        scaleCircleNavigator.setCircleCount(navigatorCount);
        scaleCircleNavigator.setNormalCircleColor(navColors[0]);
        scaleCircleNavigator.setSelectedCircleColor(navColors[1]);
        scaleCircleNavigator.setMinRadius(navRadius[0]);
        scaleCircleNavigator.setMaxRadius(navRadius[1]);

        return scaleCircleNavigator;
    }
}
