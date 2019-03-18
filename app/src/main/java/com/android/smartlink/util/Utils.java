package com.android.smartlink.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import com.android.smartlink.BuildConfig;

/**
 * User: LIUWEI
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

        return "";
    }

    public static void resetOrientation(Activity activity)
    {
        activity.setRequestedOrientation(BuildConfig.PHONE ?

                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    public static float[] getPast15Days()
    {
        float[] days = new float[16];

        for (int i = 0; i <= 15; i++)
        {
            days[i] = i;
        }

        return days;
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

    // ----------------------------------------------------------------------------------------------------------------
    // - String
    // ----------------------------------------------------------------------------------------------------------------
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

    public static String formatString(String format, Object... args)
    {
        if (args != null)
        {
            return String.format(format, args);
        }
        return format;
    }
}
