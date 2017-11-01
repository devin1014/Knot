package com.android.smartlink.ui.model;

import android.content.res.Resources;
import android.text.TextUtils;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Modules.Module;

import java.io.Serializable;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:57
 */
public class UIModule implements Serializable
{
    private static final long serialVersionUID = 179763754284831614L;

    private static final String EMPTY = "";

    private Module mModule;

    private final String POWER_KWH;

    private boolean mEditMode = false;

    private int[] mStatusColor;

    private int[] mTextStatusColor;

    public UIModule(Module module)
    {
        mModule = module;

        POWER_KWH = AppManager.getInstance().getApplication().getResources().getString(R.string.power_kwh);

        Resources resources = AppManager.getInstance().getApplication().getResources();

        mStatusColor = new int[]{resources.getColor(R.color.module_status_good), resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};

        mTextStatusColor = new int[]{resources.getColor(R.color.module_status_none), resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};

    }

    public final Module getSource()
    {
        return mModule;
    }

    public int getImageRes()
    {
        return AppManager.getInstance().getEquipmentImageRes(mModule.getId());
    }

    public int getWhiteImageRes()
    {
        return AppManager.getInstance().getEquipmentWhiteImageRes(mModule.getId());
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

    public int getColor()
    {
        if (mModule.getStatus() > mStatusColor.length || mModule.getStatus() < 0)
        {
            return mStatusColor[0];
        }

        return mStatusColor[mModule.getStatus()];
    }

    public int getTextColor()
    {
        if (mModule.getStatus() > mStatusColor.length || mModule.getStatus() < 0)
        {
            return mTextStatusColor[0];
        }

        return mTextStatusColor[mModule.getStatus()];
    }

    public int getHealth()
    {
        return mModule.getStatus();
    }

    public boolean isPowerLoadAlarm()
    {
        return mModule.getPowerLoad() >= 85;
    }

    public String getPowerLoadPercent()
    {
        return mModule.getPowerLoad() + "%";
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
