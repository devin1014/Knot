package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Configurations.ModuleInfo;
import com.android.smartlink.ui.model.BaseModule.DefaultBaseModuleImp;
import com.neulion.android.diffrecycler.diff.DataDiffCompare;

/**
 * User: LIUWEI
 * Date: 2017-11-09
 * Time: 11:46
 */
public class UIDeviceImp extends DefaultBaseModuleImp<ModuleInfo> implements DataDiffCompare<UIDeviceImp>
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

    @Override
    public boolean compareData(UIDeviceImp uiDeviceImp)
    {
        return getId() == uiDeviceImp.getId();
    }
}
