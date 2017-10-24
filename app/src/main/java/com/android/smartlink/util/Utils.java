package com.android.smartlink.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.Calendar;
import java.util.Collections;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 15:04
 */
public class Utils
{
    public static boolean isDevDebugMode(Context context)
    {
        try
        {
            ApplicationInfo applicationInfo = context.getApplicationInfo();

            return (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        catch (Exception ignored)
        {
            return true;
        }
    }

    public static float[] getPast30Days()
    {
        float[] days = new float[30];

        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 30; i++)
        {
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            days[i] = calendar.get(Calendar.DAY_OF_MONTH);
        }

        return days;
    }
}
