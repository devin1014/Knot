package com.android.smartlink.ui.model;

import com.android.smartlink.util.ui.ImageResUtil;

public class MainModuleMonitorWrapper implements UIMonitorModule
{
    private UIMonitorModule mModule;

    public MainModuleMonitorWrapper(UIMonitorModule module)
    {
        mModule = module;
    }

    @Override
    public String[] getCurrent()
    {
        return mModule.getCurrent();
    }

    @Override
    public String[] getVoltage()
    {
        return mModule.getVoltage();
    }

    @Override
    public String getPower()
    {
        return mModule.getPower();
    }

    @Override
    public String getPowerFactor()
    {
        return mModule.getPowerFactor();
    }

    @Override
    public String getEnergy()
    {
        return mModule.getEnergy();
    }

    @Override
    public int getModuleStatus()
    {
        return mModule.getModuleStatus();
    }

    @Override
    public int getPowerLoad()
    {
        return mModule.getPowerLoad();
    }

    @Override
    public int getColor()
    {
        return mModule.getColor();
    }

    @Override
    public boolean hasAlarm()
    {
        return mModule.hasAlarm();
    }

    @Override
    public boolean isToggle()
    {
        return mModule.isToggle();
    }

    @Override
    public int getSlaveID()
    {
        return mModule.getSlaveID();
    }

    @Override
    public int getChannel()
    {
        return mModule.getChannel();
    }

    @Override
    public String getName()
    {
        return mModule.getName();
    }

    @Override
    public int getId()
    {
        return mModule.getId();
    }

    @Override
    public int getImageRes()
    {
        return ImageResUtil.getImage(getId(), ImageType.DRAWABLE_LARGE_LIGHT);
    }

    @Override
    public boolean compareData(UIMonitorModule module)
    {
        return mModule.compareData(module);
    }
}
