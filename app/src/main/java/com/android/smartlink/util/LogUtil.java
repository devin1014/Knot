package com.android.smartlink.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.smartlink.Constants;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-09-26
 * Time: 18:17
 */
public class LogUtil
{
    private static boolean DEBUG = Constants.DEBUG;

    private static final String TAG = "Smartlink_";

    public static void init(Context context)
    {
        DEBUG = Utils.isDevDebugMode(context);
    }

    public static void log(Object object, String message)
    {
        if (DEBUG)
        {
            Log.d(TAG + getTagFromObject(object), message);
        }
    }

    public static void info(Object object, String message)
    {
        if (DEBUG)
        {
            Log.i(TAG + getTagFromObject(object), message);
        }
    }

    public static void warn(Object object, String message)
    {
        if (DEBUG)
        {
            Log.w(TAG + getTagFromObject(object), message);
        }
    }

    public static void error(Object object, String message)
    {
        if (DEBUG)
        {
            Log.e(TAG + getTagFromObject(object), message);
        }
    }

    private static String getTagFromObject(Object object)
    {
        if (object == null)
        {
            return "null";
        }

        return object.getClass().getSimpleName() + "@" + Integer.toHexString(object.hashCode());
    }

    public static String getViewInfo(View view)
    {
        if (view == null)
        {
            return "null";
        }

        StringBuilder builder = new StringBuilder();

        builder.append(view.getClass().getSimpleName());

        if (view.getId() != View.NO_ID)
        {
            builder.append(" @id=").append(view.getResources().getResourceEntryName(view.getId()));
        }
        else
        {
            builder.append(" @id=null");
        }

        builder.append(" @rect=[").append(view.getLeft()).append(",").append(view.getPaddingTop()).append(",").append(view.getRight()).append(",").append(view.getBottom()).append("]");

        return builder.toString();
    }
}
