package com.android.smartlink.ui.model;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:38
 */
public class UISetting
{
    private String mName;

    private String mImage;

    public UISetting(String name, String image)
    {
        mName = name;

        mImage = image;
    }

    public String getName()
    {
        return mName;
    }

    public String getImage()
    {
        return mImage;
    }
}
