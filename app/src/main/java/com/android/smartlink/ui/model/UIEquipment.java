package com.android.smartlink.ui.model;

import com.android.devin.core.ui.widget.recyclerview.IDiffCompare;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.util.ViewUtil;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-09
 * Time: 11:46
 */
public class UIEquipment implements IModule, IDiffCompare<UIEquipment>
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
    public int getImageRes()
    {
        return ViewUtil.getDrawable(AppManager.getInstance().getApplication(), mSource.getIcon());
    }

    public boolean isEditMode()
    {
        return mEditMode;
    }

    public void setEditMode(boolean editMode)
    {
        mEditMode = editMode;
    }

    @Override
    public boolean compareObject(UIEquipment obj)
    {
        return getId() == obj.getId();
    }

    @Override
    public boolean compareContent(UIEquipment obj)
    {
        return getName().equals(obj.getName());
    }

    @Override
    public Object getChangePayload()
    {
        return null;
    }
}
