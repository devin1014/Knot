package com.android.smartlink.application.manager;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.smartlink.Constants;
import com.android.smartlink.bean.Equipments;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.widget.adapter.SuggestPagerAdapter;

import java.util.Date;
import java.util.List;

/**
 * User: LIUWEI
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

    private final Application mApplication;

    private SharedPreferences mSharedPreferences;

    private EquipmentManager mEquipmentManager;

    private Weather mWeather;

    private boolean mPhoneType;

    private AppManager(Application application)
    {
        mApplication = application;

        mSharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

        mEquipmentManager = new EquipmentManager(application);

        mPhoneType = application.getResources().getConfiguration().smallestScreenWidthDp < 640;
    }

    // -----------------------------------
    // ------------ 初始化 ----------------
    public boolean isInitialized()
    {
        return mApplication != null && mEquipmentManager.isInitialized();
    }

    public Application getApplication()
    {
        return mApplication;
    }

    public String getString(int resId)
    {
        return mApplication.getResources().getString(resId);
    }

    public String[] getStringArray(int resId)
    {
        return mApplication.getResources().getStringArray(resId);
    }

    public boolean isPhone()
    {
        return mPhoneType;
    }

    // -----------------------------------
    // ------------ Weather&Location -----
    public Weather getWeather()
    {
        if (mWeather != null)
        {
            try
            {
                Date date = mWeather.getBasic().getUpdateTime();

                long deltaTime = Math.abs(System.currentTimeMillis() - date.getTime());

                if (deltaTime <= Constants.ONE_DAY && deltaTime > 0)
                {
                    return mWeather;
                }
            }
            catch (Exception ignored)
            {
            }
        }

        return null;
    }

    public void setWeather(Weather weather)
    {
        mWeather = weather;
    }

    public void setLocation(String location)
    {
        mSharedPreferences.edit().putString(Constants.KEY_SHARE_PREFERENCE_LOCATION, location).apply();

        mSharedPreferences.edit().putLong(Constants.KEY_SHARE_PREFERENCE_LOCATION_TIME, System.currentTimeMillis()).apply();
    }

    public String getLocation()
    {
        long time = mSharedPreferences.getLong(Constants.KEY_SHARE_PREFERENCE_LOCATION_TIME, -1L);

        if (time < 0 || (System.currentTimeMillis() - time) >= Constants.ONE_DAY)
        {
            return null;
        }

        return mSharedPreferences.getString(Constants.KEY_SHARE_PREFERENCE_LOCATION, null);
    }

    // -----------------------------------
    // ------------ Equipment ------------
    public void setEquipmentName(int id, String name)
    {
        mEquipmentManager.update(id, name);
    }

    public String getEquipmentName(int id)
    {
        return mEquipmentManager.getName(id);
    }

    public void setEquipments(Equipments equipments)
    {
        mEquipmentManager.setEquipments(equipments);
    }

    public Equipment getEquipment(int id)
    {
        return mEquipmentManager.getEquipment(id);
    }

    public List<Equipment> getEquipments()
    {
        return mEquipmentManager.getEquipments();
    }

    // -----------------------------------
    // ------------ Demo status ----------
    public void setDemoMode(boolean demoMode)
    {
        mSharedPreferences.edit().putBoolean(Constants.KEY_SHARE_PREFERENCE_DEMO_MODE, demoMode).apply();
    }

    public boolean isDemoMode()
    {
        return mSharedPreferences.getBoolean(Constants.KEY_SHARE_PREFERENCE_DEMO_MODE, isPhone());
    }

    public void setDemoModeStatus(int status)
    {
        mSharedPreferences.edit().putInt(Constants.KEY_SHARE_PREFERENCE_DEMO_STATUS, status).apply();
    }

    public int getDemoModeStatus()
    {
        return mSharedPreferences.getInt(Constants.KEY_SHARE_PREFERENCE_DEMO_STATUS, Constants.STATUS_NORMAL);
    }

    // -----------------------------------
    // ------------ Suggest --------------
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

}
