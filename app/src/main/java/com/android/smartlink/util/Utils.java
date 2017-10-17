package com.android.smartlink.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

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
}
