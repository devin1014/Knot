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

    public static UIModule newWeatherInstance()
    {
        return new UIModule(new Module(), UIModule.TYPE_WEATHER);
    }

    public static UIModule newStatusInstance()
    {
        return new UIModule(new Module(), UIModule.TYPE_STATUS);
    }

    public static final int TYPE_NORMAL = 1;

    public static final int TYPE_STATUS = 2;

    public static final int TYPE_WEATHER = 3;

    private static final String EMPTY = "";

    private Module mModule;

    private final String POWER_KWH;

    private int mType;

    private boolean mEditMode = false;

    private int[] mStatusColor;

    private int[] mTextStatusColor;

    public UIModule(Module module)
    {
        this(module, TYPE_NORMAL);
    }

    public UIModule(Module module, int type)
    {
        mModule = module;

        mType = type;

        POWER_KWH = AppManager.getInstance().getApplication().getResources().getString(R.string.power_kwh);

        Resources resources = AppManager.getInstance().getApplication().getResources();

        mStatusColor = new int[]{resources.getColor(R.color.module_status_none), resources.getColor(R.color.module_status_good),
                resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};

        mTextStatusColor = new int[]{resources.getColor(R.color.module_status_none), resources.getColor(R.color.module_status_none),
                resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};
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

    public String getPowerAndMonth()
    {
        return getPower() + " " + getMonth();
    }

    public String getStatus()
    {
        return AppManager.getInstance().getModuleStatus(mModule.getStatus());
    }

    public int getColor()
    {
        if (mModule.getStatus() > 3 || mModule.getStatus() < 0)
        {
            return mStatusColor[0];
        }

        return mStatusColor[mModule.getStatus()];
    }

    public int getTextColor()
    {
        if (mModule.getStatus() > 3 || mModule.getStatus() < 0)
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

    public String getPowerLoadDescription()
    {
        if (mModule.getPowerLoad() >= 85)
        {
            return AppManager.getInstance().getString(R.string.power_load_status_alert);
        }

        return AppManager.getInstance().getString(R.string.power_load_status_ok);
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
