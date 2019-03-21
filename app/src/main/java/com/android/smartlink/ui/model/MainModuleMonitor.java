package com.android.smartlink.ui.model;

import android.content.res.Resources;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.IModuleAddress;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.util.Utils;
import com.android.smartlink.util.ui.ImageResUtil;

public class MainModuleMonitor implements UIMonitorModule
{
    private int mPowerLoad;

    private int mProgressColor;

    public MainModuleMonitor(ModulesData data)
    {
        float power = 0f;

        for (MonitorModuleData d : data.getMonitorModules())
        {
            try
            {
                power += Float.valueOf(d.getPower());
            }
            catch (Exception ignored)
            {
            }
        }

        power = power / data.getMonitorModules().size();

        mPowerLoad = Math.min((int) (power / 5f * 100), 100);

        if (mPowerLoad < 10)
        {
            mPowerLoad = Utils.getRandomInt(10, 15);
        }

        Resources resources = AppManager.getInstance().getApplication().getResources();

        mProgressColor = resources.getColor(R.color.module_status_good);
    }

    @Override
    public String[] getCurrent()
    {
        return new String[0];
    }

    @Override
    public String[] getVoltage()
    {
        return new String[0];
    }

    @Override
    public String getPower()
    {
        return null;
    }

    @Override
    public String getPowerFactor()
    {
        return null;
    }

    @Override
    public String getEnergy()
    {
        return null;
    }

    @Override
    public int getModuleStatus()
    {
        return Status.STATUS_NORMAL;
    }

    @Override
    public int getPowerLoad()
    {
        return mPowerLoad;
    }

    @Override
    public int getColor()
    {
        return mProgressColor;
    }

    @Override
    public boolean hasAlarm()
    {
        return false;
    }

    @Override
    public boolean isToggle()
    {
        return false;
    }

    @Override
    public int getSlaveID()
    {
        return IModuleAddress.MONITOR_SLAVE_ID_ALL;
    }

    @Override
    public int getChannel()
    {
        return IModuleAddress.MONITOR_CHANNEL;
    }

    @Override
    public String getName()
    {
        return "总电源";
    }

    @Override
    public int getId()
    {
        return ModuleParser.generateId(getSlaveID(), getChannel());
    }

    @Override
    public int getImageRes()
    {
        return ImageResUtil.getImage(getId(), ImageType.DRAWABLE_NORMAL_LIGHT);
    }

    @Override
    public boolean compareData(UIMonitorModule uiMonitorModule)
    {
        return getId() == uiMonitorModule.getId();
    }
}
