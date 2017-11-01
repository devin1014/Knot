package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Modules.Module;

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

    public UIFilter(Module module)
    {
        mId = module.getId();

        mName = AppManager.getInstance().getEquipmentName(module.getId());

        mChecked = true;
    }

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
