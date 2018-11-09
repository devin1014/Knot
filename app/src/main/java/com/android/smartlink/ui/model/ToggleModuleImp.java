package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.ModulesData.ToggleModuleData;
import com.android.smartlink.ui.model.BaseModule.DefaultSourceModuleImp;
import com.android.smartlink.util.Helper.ToggleHelper;
import com.android.smartlink.util.ImageResUtil;
import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.io.Serializable;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:58
 */
public class ToggleModuleImp extends DefaultSourceModuleImp implements Serializable, IModule
{
    private static final long serialVersionUID = -2506753032922419481L;

    private ToggleModuleData mToggle;

    private int mImageType;

    @DiffItem
    int mId;

    public ToggleModuleImp(ToggleModuleData toggle)
    {
        this(toggle, ImageType.DRAWABLE_NORMAL);
    }

    public ToggleModuleImp(ToggleModuleData toggle, @ImageType int imageType)
    {
        super(toggle);

        mId = getId();

        mToggle = toggle;

        toggleOn = ToggleHelper.isToggleOn(toggle);

        mImageType = imageType;
    }

    public int getImageRes()
    {
        return ImageResUtil.getImage(mToggle.getChannel(), mImageType);
    }

    @Override
    public String getName()
    {
        return AppManager.getInstance().getModuleName(getId());
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
