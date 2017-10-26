package com.android.smartlink.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import java.util.Calendar;

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

    public static String getApplicationMetaData(Context context, String key)
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
        float[] days = new float[30];

        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 30; i++)
        {
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            days[i] = calendar.get(Calendar.DAY_OF_MONTH);
        }

        return days;
    }

    public static void setSpannable(SpannableStringBuilder spannableString, int start, int end, Object... objects)
    {
        if (objects != null)
        {
            for (Object obj : objects)
            {
                spannableString.setSpan(obj, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public static boolean isEmpty(int... params)
    {
        return params == null || params.length == 0;
    }
}
