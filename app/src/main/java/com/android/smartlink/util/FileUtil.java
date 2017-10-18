package com.android.smartlink.util;

import android.os.Environment;

import com.android.smartlink.bean.Weather;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 14:35
 */
public class FileUtil
{
    private static final String FILE_NAME = "weather.json";

    public static boolean writeWeatherFile(Weather weather)
    {
        ObjectOutputStream fos = null;

        try
        {
            File file = Environment.getExternalStoragePublicDirectory(FILE_NAME);

            if (!file.exists())
            {
                if (!file.createNewFile())
                {
                    //// TODO: 2017/10/18
                    return false;
                }
            }

            fos = new ObjectOutputStream(new FileOutputStream(file));

            fos.writeObject(weather);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (fos != null)
                {
                    fos.close();
                }
            }
            catch (IOException ignored)
            {
            }
        }

        return false;
    }

    public static boolean hasWeatherCache()
    {
        return Environment.getExternalStoragePublicDirectory(FILE_NAME).exists();
    }

    public static Weather readWeatherFile()
    {
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream(new FileInputStream(Environment.getExternalStoragePublicDirectory(FILE_NAME)));

            return (Weather) ois.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (ois != null)
                {
                    ois.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}
