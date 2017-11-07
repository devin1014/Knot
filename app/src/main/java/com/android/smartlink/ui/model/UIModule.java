package com.android.smartlink.ui.model;

import android.content.res.Resources;
import android.text.TextUtils;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Modules.Module;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:57
 */
public class UIModule implements Serializable, UIAlarm
{
    private static final long serialVersionUID = 179763754284831614L;

    private Module mModule;

    private final String POWER_KWH;

    private boolean mEditMode = false;

    private int[] mStatusColor;

    private int[] mTextStatusColor;

    private DecimalFormat mNumberFormat = new DecimalFormat("0.0");

    public UIModule(Module module)
    {
        mModule = module;

        POWER_KWH = AppManager.getInstance().getApplication().getResources().getString(R.string.format_power);

        Resources resources = AppManager.getInstance().getApplication().getResources();

        mStatusColor = new int[]{resources.getColor(R.color.module_status_good), resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};

        mTextStatusColor = new int[]{resources.getColor(R.color.module_status_none), resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};
    }

    public int getId()
    {
        return mModule.getId();
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
        String equipmentName = AppManager.getInstance().getEquipmentName(mModule.getId());

        if (TextUtils.isEmpty(equipmentName))
        {
            Equipment equipment = AppManager.getInstance().getEquipment(mModule.getId());

            return equipment != null ? equipment.getName() : null;
        }

        return equipmentName;
    }

    public String getEnergy()
    {
        return String.format(POWER_KWH, mNumberFormat.format(mModule.getEnergy()));
    }

    public String getStatusFormat()
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
        if (mModule.getStatus() > mTextStatusColor.length || mModule.getStatus() < 0)
        {
            return mTextStatusColor[0];
        }

        return mTextStatusColor[mModule.getStatus()];
    }

    public int getStatus()
    {
        return mModule.getStatus();
    }

    public int getPowerLoad()
    {
        return (int) (mModule.getCurrent() / 5f * 100);
    }

    public boolean isPowerLoadAlarm()
    {
        return mModule.getStatus() != Constants.STATUS_NORMAL;
    }

    public String getPowerLoadPercent()
    {
        return getPowerLoad() + "%";
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
