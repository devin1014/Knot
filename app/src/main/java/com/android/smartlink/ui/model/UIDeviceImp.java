package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Configurations.ModuleInfo;
import com.android.smartlink.ui.model.BaseModule.DefaultBaseModuleImp;

/**
 * User: LIUWEI
 * Date: 2017-11-09
 * Time: 11:46
 */
public class UIDeviceImp extends DefaultBaseModuleImp<ModuleInfo>
{
    public UIDeviceImp(BaseModule source)
    {
        super(source);
    }

    @Override
    public String getName()
    {
        return AppManager.getInstance().getModuleName(getId());
    }

    private boolean mEditMode = false;

    public boolean isEditMode()
    {
        return mEditMode;
    }

    public void setEditMode(boolean editMode)
    {
        mEditMode = editMode;
    }
}
