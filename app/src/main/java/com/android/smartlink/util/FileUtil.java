package com.android.smartlink.util;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.InputStreamReader;


/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 14:35
 */
public class FileUtil
{
    public static <T> T openAssets(Activity activity, String name, Class<T> cls)
    {
        try
        {
            return new Gson().fromJson(new InputStreamReader(activity.getAssets().open(name)), cls);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }
}
