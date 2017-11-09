package com.android.smartlink.ui.model;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 17:51
 */
public class UIFilter
{
    private int mId;

    private String mName;

    private boolean mChecked = true;

    public UIFilter(int id, String name)
    {
        mId = id;

        mName = name;

        mChecked = true;
    }

    public int getId()
    {
        return mId;
    }

    public String getName()
    {
        return mName;
    }

    public boolean isChecked()
    {
        return mChecked;
    }

    public void setChecked(boolean checked)
    {
        mChecked = checked;
    }
}
