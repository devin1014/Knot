package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.ModuleConfiguration.ModuleInfo;
import com.android.smartlink.bean.Modules.Toggle;
import com.android.smartlink.util.Helper.ToggleHelper;
import com.android.smartlink.util.ImageResUtil;
import com.android.smartlink.util.ImageResUtil.ImageType;
import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.io.Serializable;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:58
 */
public class UIToggle implements Serializable, IModule
{
    private static final long serialVersionUID = -2506753032922419481L;

    private Toggle mToggle;

    public UIToggle(Toggle toggle)
    {
        mToggle = toggle;

        toggleOn = ToggleHelper.isToggleOn(toggle);
    }

    @DiffItem
    @Override
    public int getId()
    {
        return mToggle.getChannel();
    }

    public int getImageRes()
    {
        return ImageResUtil.getImage(mToggle.getChannel(), ImageType.DRAWABLE_NORMAL);
    }

    public int getWhiteImageRes()
    {
        return ImageResUtil.getImage(mToggle.getChannel(), ImageType.DRAWABLE_NORMAL_LIGHT);
    }

    @Override
    public String getName()
    {
        ModuleInfo moduleInfo = AppManager.getInstance().getToggle(mToggle.getChannel());

        return moduleInfo != null ? moduleInfo.getName() : null;
    }

    @Override
    public String getEnergy()
    {
        return null;
    }

    @DiffContent
    @Override
    public int getStatus()
    {
        return mToggle.getStatus();
    }

    @Override
    public int getPowerLoad()
    {
        return 0;
    }

    @Override
    public int getColor()
    {
        return 0;
    }

    @Override
    public boolean isNormal()
    {
        return true;
    }

    @Override
    public boolean isToggle()
    {
        return true;
    }

    private boolean toggleOn;

    public void setToggleOn(boolean on)
    {
        toggleOn = on;
    }

    @DiffContent
    public boolean isToggleOn()
    {
        return toggleOn;
    }

    public int getDeviceId()
    {
        return mToggle.getSlaveID();
    }
}
