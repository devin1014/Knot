package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-09
 * Time: 11:46
 */
public class UIEquipment implements IModule
{
    private Equipment mSource;

    private boolean mEditMode = false;

    public UIEquipment(Equipment equipment)
    {
        mSource = equipment;
    }

    @Override
    public int getId()
    {
        return mSource.getId();
    }

    @Override
    public String getName()
    {
        return AppManager.getInstance().getEquipmentName(mSource.getId());
    }

    @Override
    public String getImageName()
    {
        return null;
    }

    @Override
    public int getImageRes()
    {
        return AppManager.getInstance().getEquipmentImageRes(mSource.getId());
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
