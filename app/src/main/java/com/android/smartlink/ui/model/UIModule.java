package com.android.smartlink.ui.model;

import android.text.TextUtils;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Modules.Module;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:57
 */
public class UIModule
{
    public static final int TYPE_NORMAL = 1;

    public static final int TYPE_STATUS = 2;

    private static final String EMPTY = "";

    private Module mModule;

    private final String POWER_KWH;

    private int mType;

    private boolean mEditMode = false;

    public UIModule(Module module)
    {
        this(module, TYPE_NORMAL);
    }

    public UIModule(Module module, int type)
    {
        mModule = module;

        mType = type;

        POWER_KWH = AppManager.getInstance().getApplication().getResources().getString(R.string.power_kwh);
    }

    public final Module getSource()
    {
        return mModule;
    }

    public int getImageRes()
    {
        return AppManager.getInstance().getEquipmentImageRes(mModule.getId());
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

    public String getNameAndStatus()
    {
        return getName() + getStatus();
    }

    public boolean consumePower()
    {
        return !TextUtils.isEmpty(mModule.getPower());
    }

    public String getPower()
    {
        if (TextUtils.isEmpty(mModule.getPower()))
        {
            return EMPTY;
        }

        return String.format(POWER_KWH, mModule.getPower());
    }

    public String getMonth()
    {
        return AppManager.getInstance().getCurrentMonth();
    }

    public String getStatus()
    {
        return AppManager.getInstance().getModuleStatus(mModule.getStatus());
    }

    public int getHealth()
    {
        return mModule.getStatus();
    }

    public int getType()
    {
        return mType;
    }

    public boolean isItem()
    {
        return mType == TYPE_NORMAL;
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
