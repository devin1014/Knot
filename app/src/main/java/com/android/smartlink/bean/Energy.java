package com.android.smartlink.bean;

/**
 * User: LIUWEI
 * Date: 2017-10-19
 * Time: 11:16
 */
public class Energy
{
    private float[] data; // 过去30天，每天用电量

    public float[] getData()
    {
        return data;
    }

    public float getTotal()
    {
        if (data != null && data.length > 0)
        {
            float total = 0f;

            for (float f : data)
            {
                total += f;
            }

            return total;
        }

        return 0f;
    }
}
