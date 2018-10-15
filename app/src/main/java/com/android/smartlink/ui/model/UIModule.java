package com.android.smartlink.ui.model;

import android.content.res.Resources;
import android.text.TextUtils;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Modbus.Equipment;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.util.ViewUtil;
import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 15:57
 */
public class UIModule implements Serializable, IModule
{
    private static final long serialVersionUID = 179763754284831614L;

    @DiffItem
    int mId;

    @DiffContent
    int mStatus;

    @DiffContent
    String mEnergy;

    private Module mModule;

    private final String POWER_KWH;

    private int[] mStatusColor;

    private int[] mTextStatusColor;

    private int mPowerLoad = 0;

    private String[] mStatusArray;

    private DecimalFormat mNumberFormat = new DecimalFormat("0.0");

    public UIModule(Module module)
    {
        mModule = module;

        POWER_KWH = AppManager.getInstance().getApplication().getResources().getString(R.string.format_power);

        mId = module.getId();

        mEnergy = module.getEnergy();

        mStatus = module.getStatus();

        Resources resources = AppManager.getInstance().getApplication().getResources();

        int green = resources.getColor(R.color.module_status_good);

        int red = resources.getColor(R.color.module_status_error);

        int yellow = resources.getColor(R.color.module_status_warn);

        int white = resources.getColor(R.color.module_status_none);

        mStatusColor = new int[]{green, red, yellow};

        mTextStatusColor = new int[]{white, red, yellow};

        mStatusArray = AppManager.getInstance().getStringArray(R.array.module_status_array);

        if (!TextUtils.isEmpty(mModule.getCurrent()))
        {
            mPowerLoad = Math.min((int) (Float.valueOf(mModule.getCurrent()) / 5f * 100), 100);
        }
    }

    public int getId()
    {
        return mModule.getId();
    }

    public int getImageRes()
    {
        Equipment equipment = AppManager.getInstance().getEquipment(mModule.getId());

        if (equipment != null)
        {
            return ViewUtil.getDrawable(AppManager.getInstance().getApplication(), equipment.getIcon());
        }
        else
        {
            return R.mipmap.ic_launcher;
        }
    }

    public int getWhiteImageRes()
    {
        Equipment equipment = AppManager.getInstance().getEquipment(mModule.getId());

        if (equipment != null)
        {
            return ViewUtil.getDrawable(AppManager.getInstance().getApplication(), equipment.getLightIcon());
        }
        else
        {
            return R.mipmap.ic_launcher;
        }
    }

    public String getName()
    {
        return AppManager.getInstance().getEquipmentName(mModule.getId());
    }

    public String getEnergy()
    {
        if (TextUtils.isEmpty(mModule.getEnergy()))
        {
            return "";
        }

        return String.format(POWER_KWH, mNumberFormat.format(Float.valueOf(mModule.getEnergy())));
    }

    public String getStatusFormat()
    {
        return mStatusArray[adjustStatus()];
    }

    public int getColor()
    {
        return mStatusColor[adjustStatus() % mStatusColor.length];
    }

    public int getTextColor()
    {
        return mTextStatusColor[adjustStatus()];
    }

    public int getStatus()
    {
        return adjustStatus();
    }

    @DiffContent
    public int getPowerLoad()
    {
        return mPowerLoad;
    }

    @Override
    public boolean isToggle()
    {
        return false;
    }

    public boolean isNormal()
    {
        return !isAlarm() && (mModule.getStatus() == Constants.STATUS_NORMAL);
    }

    public boolean isAlarm()
    {
        int status = mModule.getStatus();

        return status == Constants.STATUS_NORMAL && getPowerLoad() >= Constants.POWER_LOAD_ALARM;
    }

    public boolean isError()
    {
        int status = mModule.getStatus();

        return status == Constants.STATUS_ERROR;

    }

    public String getPowerLoadPercent()
    {
        return getPowerLoad() + "%";
    }

    private int adjustStatus()
    {
        if (isAlarm())
        {
            return Constants.STATUS_WARNING;
        }

        return mModule.getStatus();
    }

    public static int getStatus(Module module)
    {
        int status = module.getStatus();

        int powerLoad = 0;

        if (!TextUtils.isEmpty(module.getCurrent()))
        {
            powerLoad = Math.min((int) (Float.valueOf(module.getCurrent()) / 5f * 100), 100);
        }

        if (status == Constants.STATUS_NORMAL && powerLoad >= Constants.POWER_LOAD_ALARM)
        {
            return Constants.STATUS_WARNING;
        }

        return module.getStatus();
    }
}
