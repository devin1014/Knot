package com.android.smartlink.application.manager;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.bean.Equipments;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.widget.adapter.SuggestPagerAdapter;
import com.android.smartlink.util.ViewUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:36
 */
public class AppManager
{
    @SuppressLint("StaticFieldLeak")
    private static AppManager sAppManager;

    public static AppManager getInstance()
    {
        return sAppManager;
    }

    public static void initialize(Application application)
    {
        if (sAppManager == null)
        {
            sAppManager = new AppManager(application);
        }
    }

    private final String KEY_EQUIPMENT_NAME = getClass().getSimpleName() + "_equipment";

    private final Application mApplication;

    private SharedPreferences mSharedPreferences;

    private List<Module> mModules;

    private List<Equipment> mEquipments;

    private Weather mWeather;

    private AppManager(Application application)
    {
        mApplication = application;

        mSharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
    }

    public boolean isInitialized()
    {
        return mApplication != null && mEquipments != null && mEquipments.size() > 0;
    }

    public Application getApplication()
    {
        return mApplication;
    }

    public String getCurrentMonth()
    {
        return mApplication.getResources().getStringArray(R.array.month_array)[Calendar.getInstance().get(Calendar.MONTH)];
    }

    public String getModuleStatus(int status)
    {
        return mApplication.getResources().getStringArray(R.array.module_status_array)[status];
    }

    public void setModules(List<Module> modules)
    {
        mModules = modules;
    }

    public List<Module> getModules()
    {
        return mModules;
    }

    public List<Module> getModules(int... ids)
    {
        if (ids == null || ids.length == 0)
        {
            return mModules;
        }

        List<Module> list = new ArrayList<>(1);

        for (Module module : mModules)
        {
            for (int id : ids)
            {
                if (module.getId() == id)
                {
                    list.add(module);

                    break;
                }
            }
        }

        return list;
    }

    public boolean checkWeather()
    {
        if (mWeather != null)
        {
            try
            {
                Date date = mWeather.getBasic().getUpdateTime();

                long deltaTime = Math.abs(System.currentTimeMillis() - date.getTime());

                if (deltaTime <= Constants.SIX_HOUR && deltaTime > 0)
                {
                    return true;
                }
            }
            catch (Exception ignored)
            {
            }
        }

        return false;
    }

    public Weather getWeather()
    {
        return mWeather;
    }

    public void setWeather(Weather weather)
    {
        mWeather = weather;
    }

    // ---- Equipment ----------------
    public void setEquipmentName(String id, String name)
    {
        mSharedPreferences.edit().putString(KEY_EQUIPMENT_NAME + id, name).apply();
    }

    public String getEquipmentName(String id, String defaultName)
    {
        return mSharedPreferences.getString(KEY_EQUIPMENT_NAME + id, defaultName);
    }

    public void setEquipments(Equipments equipments)
    {
        if (equipments == null)
        {
            throw new IllegalArgumentException("can not parse equipment.json file");
        }

        if (equipments.getEquipments() == null || equipments.getEquipments().size() == 0)
        {
            throw new IllegalArgumentException("can not parse equipment.json file");
        }

        mEquipments = equipments.getEquipments();

        for (Equipment equipment : mEquipments)
        {
            setEquipmentName(String.valueOf(equipment.getId()), getEquipmentName(String.valueOf(equipment.getId()), equipment.getName()));
        }
    }

    public Equipment getEquipment(int id)
    {
        if (mEquipments != null)
        {
            for (Equipment e : mEquipments)
            {
                if (e.getId() == id)
                {
                    return e;
                }
            }
        }

        return null;
    }

    public int getEquipmentImageRes(int id)
    {
        Equipment equipment = getEquipment(id);

        if (equipment != null)
        {
            return ViewUtil.getDrawable(mApplication, equipment.getIcon());
        }

        return R.mipmap.ic_launcher;
    }

    public int getEquipmentWhiteImageRes(int id)
    {
        Equipment equipment = getEquipment(id);

        if (equipment != null)
        {
            return ViewUtil.getDrawable(mApplication, equipment.getLightIcon());
        }

        return R.mipmap.ic_launcher;
    }

    public String getString(int resId)
    {
        return mApplication.getResources().getString(resId);
    }

    public String[] getStringArray(int resId)
    {
        return mApplication.getResources().getStringArray(resId);
    }

    public int getEnergySuggestIndex()
    {
        final int index = mSharedPreferences.getInt(Constants.KEY_SHARE_PREFERENCE_SUGGEST, 0);

        int nextIndex = index + 1;

        if (nextIndex >= SuggestPagerAdapter.SUGGESTIONS.length)
        {
            nextIndex = 0;
        }

        mSharedPreferences.edit().putInt(Constants.KEY_SHARE_PREFERENCE_SUGGEST, nextIndex).apply();

        return index;
    }

    public void setLocation(String location)
    {
        mSharedPreferences.edit().putString(Constants.KEY_SHARE_PREFERENCE_LOCATION, location).apply();

        mSharedPreferences.edit().putLong(Constants.KEY_SHARE_PREFERENCE_LOCATION_TIME, System.currentTimeMillis()).apply();
    }

    public String getLocation()
    {
        long time = mSharedPreferences.getLong(Constants.KEY_SHARE_PREFERENCE_LOCATION_TIME, -1L);

        if (time < 0 || (System.currentTimeMillis() - time) >= Constants.SIX_HOUR)
        {
            return null;
        }

        return mSharedPreferences.getString(Constants.KEY_SHARE_PREFERENCE_LOCATION, null);
    }
}
