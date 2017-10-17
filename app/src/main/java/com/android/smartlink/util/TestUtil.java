package com.android.smartlink.util;

import android.os.SystemClock;

import com.android.smartlink.Constants;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 15:09
 */
public class TestUtil
{
    private static final boolean DEBUG = Constants.DEBUG;

    private static long time;

    public static void set()
    {
        if (DEBUG)
        {
            time = SystemClock.uptimeMillis();
        }
    }

    public static void test(Object object, String where)
    {
        if (DEBUG)
        {
            final long duration = SystemClock.uptimeMillis() - time;

            LogUtil.log(object, where + " #{" + duration + "ms}");
        }

        time = 0;
    }
}
