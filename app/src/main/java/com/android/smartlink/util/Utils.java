package com.android.smartlink.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

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

    static String getApplicationMetaData(Context context, String key)
    {
        try
        {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            return applicationInfo.metaData.getString(key);
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static float[] getPast30Days()
    {
        float[] days = new float[31];

        for (int i = 0; i <= 30; i++)
        {
            days[i] = i;
        }

        return days;
    }

    public static boolean isEmpty(int... params)
    {
        return params == null || params.length == 0;
    }

}
