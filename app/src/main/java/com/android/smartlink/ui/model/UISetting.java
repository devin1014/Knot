package com.android.smartlink.ui.model;

import com.android.devin.core.ui.widget.recyclerview.IDiffCompare;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:38
 */
public class UISetting implements IDiffCompare<UISetting>
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

    @Override
    public boolean compareObject(UISetting uiSetting)
    {
        return mName.equals(uiSetting.getName());
    }

    @Override
    public boolean compareContent(UISetting uiSetting)
    {
        return mName.equals(uiSetting.getName());
    }

    @Override
    public Object getChangePayload()
    {
        return null;
    }
}
