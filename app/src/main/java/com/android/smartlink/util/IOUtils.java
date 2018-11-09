package com.android.smartlink.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: LIUWEI
 * Date: 2017-11-02
 * Time: 15:29
 */
public class IOUtils
{
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
