package com.android.smartlink.ui.model;

import com.android.smartlink.Constants.MODULE_FLAG;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.ModulesData.ToggleModuleData;
import com.android.smartlink.ui.model.BaseModule.DefaultBaseModuleImp;
import com.android.smartlink.ui.model.UIMonitorModule.ImageType;
import com.android.smartlink.util.ImageResUtil;
import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.io.Serializable;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:58
 */
public class ToggleModuleImp extends DefaultBaseModuleImp implements Serializable, UIToggleModule
{
    private static final long serialVersionUID = -2506753032922419481L;

    private int mImageType;

    @DiffItem
    final int mId;

    @DiffContent
    int mStatus;

    public ToggleModuleImp(ToggleModuleData toggle)
    {
        this(toggle, ImageType.DRAWABLE_NORMAL);
    }

    public ToggleModuleImp(ToggleModuleData toggle, @ImageType int imageType)
    {
        super(toggle);

        mId = getId();

        mStatus = toggle.getStatus();

        mImageType = imageType;
    }

    public int getImageRes()
    {
        return ImageResUtil.getImage(getId(), mImageType);
    }

    @Override
    public String getName()
    {
        return AppManager.getInstance().getModuleName(getId());
    }

    @Override
    public int getStatus()
    {
        return mStatus;
    }

    @Override
    public boolean toggle()
    {
        if (mStatus == MODULE_FLAG.STATUS_ON.value)
        {
            mStatus = MODULE_FLAG.STATUS_OFF.value;

            return false;
        }
        else if (mStatus == MODULE_FLAG.STATUS_OFF.value)
        {
            mStatus = MODULE_FLAG.STATUS_ON.value;

            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ToggleModuleImp)
        {
            return getId() == ((ToggleModuleImp) obj).getId();
        }

        return super.equals(obj);
    }

}
