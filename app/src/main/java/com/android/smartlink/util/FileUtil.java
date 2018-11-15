package com.android.smartlink.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 14:35
 */
public class FileUtil
{
    public static <T> T openAssets(Context context, String name, Class<T> cls)
    {
        return openAssets(context.getAssets(), name, cls);
    }

    public static <T> T openAssets(AssetManager assetManager, String name, Class<T> cls)
    {
        try
        {
            return new Gson().fromJson(new InputStreamReader(assetManager.open(name)), cls);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return null;
        }
    }

    public static String parseStream(InputStream inputStream) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        String s;

        StringBuilder builder = new StringBuilder();

        while ((s = reader.readLine()) != null)
        {
            builder.append(s);
        }

        reader.close();

        return builder.toString();
    }
}
