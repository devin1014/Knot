package com.android.devin.core.util;

import android.app.Activity;
import android.support.annotation.FloatRange;
import android.view.Window;
import android.view.WindowManager;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-12
 * Time: 17:07
 */
public class ScreenBrightnessUtil
{
    @FloatRange(from = 0f, to = 1f)
    public static float getScreenBrightness(Activity activity)
    {
        return activity.getWindow().getAttributes().screenBrightness;
    }

    public static void setScreenBrightness(Activity activity, @FloatRange(from = 0f, to = 1f) float brightness)
    {
        Window window = activity.getWindow();

        WindowManager.LayoutParams lp = window.getAttributes();

        lp.screenBrightness = brightness;

        window.setAttributes(lp);
    }
}
