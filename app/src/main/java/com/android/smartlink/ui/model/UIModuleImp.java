package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Configurations.ModuleInfo;
import com.android.smartlink.ui.model.BaseModule.DefaultSourceModuleImp;
import com.android.smartlink.ui.model.IModule.ImageType;
import com.android.smartlink.util.ImageResUtil;

/**
 * User: LIUWEI
 * Date: 2017-11-09
 * Time: 11:46
 */
public class UIModuleImp extends DefaultSourceModuleImp<ModuleInfo>
{
    private boolean mEditMode = false;

    public UIModuleImp(BaseModule source)
    {
        super(source);
    }

    @Override
    public String getName()
    {
        return AppManager.getInstance().getModuleName(getId());
    }

    @Override
    public int getImageRes()
    {
        return ImageResUtil.getImage(getId(), ImageType.DRAWABLE_NORMAL);
    }

    public boolean isEditMode()
    {
        return mEditMode;
    }

    public void setEditMode(boolean editMode)
    {
        mEditMode = editMode;
    }
}
