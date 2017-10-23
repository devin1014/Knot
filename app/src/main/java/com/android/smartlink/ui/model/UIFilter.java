package com.android.smartlink.ui.model;

import android.text.TextUtils;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Modules.Module;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 17:51
 */
public class UIFilter
{
    private Module mModule;

    private boolean mChecked;

    public UIFilter(Module module)
    {
        mModule = module;

        mChecked = true;
    }

    public int getId()
    {
        return mModule.getId();
    }

    public String getName()
    {
        String equipmentName = AppManager.getInstance().getEquipmentName(String.valueOf(mModule.getId()), null);

        if (TextUtils.isEmpty(equipmentName))
        {
            Equipment equipment = AppManager.getInstance().getEquipment(mModule.getId());

            return equipment != null ? equipment.getName() : null;
        }

        return equipmentName;
    }

    public boolean isChecked()
    {
        return mChecked;
    }

    public void setChecked(boolean checked)
    {
        mChecked = checked;
    }
}
