package com.android.smartlink.util;

import android.app.Activity;


/**
 * User: LIUWEI
 * Date: 2017-12-12
 * Time: 17:13
 */
public class ScreenUtil extends ScreenBrightnessUtil
{
    private static final float BRIGHTNESS_MODULE = 0.75f;

    private static final float BRIGHTNESS_CLOCK = 0.25f;

    public static void showModule(Activity activity)
    {
        setScreenBrightness(activity, BRIGHTNESS_MODULE);
    }

    public static void showClock(Activity activity)
    {
        setScreenBrightness(activity, BRIGHTNESS_CLOCK);
    }
}
