package com.android.smartlink.util;

import com.google.gson.Gson;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:35
 */
public class ConvertUtil
{
    public static <T> T convertResponse(okhttp3.Response response, T t)
    {
        if (response == null || response.body() == null)
        {
            return null;
        }



        return null;
    }
}
