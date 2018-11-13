package com.android.smartlink.bean;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.model.BaseModule;

public class AllFilter implements BaseModule
{
    @Override
    public int getSlaveID()
    {
        return Constants.ID_ALL;
    }

    @Override
    public int getChannel()
    {
        return Constants.ID_ALL;
    }

    @Override
    public String getName()
    {
        return AppManager.getInstance().getString(R.string.events_filter_other);
    }
}
