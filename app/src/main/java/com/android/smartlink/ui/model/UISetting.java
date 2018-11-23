package com.android.smartlink.ui.model;

import com.neulion.android.diffrecycler.annotation.DiffItem;
import com.neulion.android.diffrecycler.diff.DataDiffCompare;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 16:38
 */
public class UISetting implements DataDiffCompare<UISetting>
{
    @DiffItem
    String mName;

    String mImage;

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
    public boolean compareData(UISetting uiSetting)
    {
        return getName().equalsIgnoreCase(uiSetting.getName());
    }
}
