package com.android.smartlink.application.manager;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntDef;

import com.android.smartlink.Constants;
import com.android.smartlink.bean.Configurations;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.bean.RequestUrl.HttpUrl;
import com.android.smartlink.bean.RequestUrl.LocalUrl;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.model.BaseModule;
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

    @IntDef({RequestMode.MODE_LOCAL, RequestMode.MODE_HTTP, RequestMode.MODE_REMOTE})
    public @interface RequestMode
    {
        int MODE_LOCAL = 0;
        int MODE_HTTP = 1;
        int MODE_REMOTE = 2;
    }

    private final Application mApplication;

    private SharedPreferences mSharedPreferences;

    private ModuleManager mModuleManager;

    private Weather mWeather;

    //private boolean mPhoneType;

    private AppManager(Application application)
    {
        mApplication = application;

        mSharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

        mModuleManager = new ModuleManager(application);

        //mPhoneType = application.getResources().getConfiguration().smallestScreenWidthDp < 600;
    }

    // ---------------------------------------------------------------------------------------------------------
    // ------------ 初始化 --------------------------------------------------------------------------------------
    public boolean isInitialized()
    {
        return mApplication != null && mModuleManager.isInitialized();
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

    //    public boolean isPhone()
    //    {
    //        return mPhoneType;
    //    }

    // ---------------------------------------------------------------------------------------------------------
    // ------------ Weather&Location ---------------------------------------------------------------------------
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

    // ---------------------------------------------------------------------------------------------------------
    // ------------  Modules  ----------------------------------------------------------------------------------
    public void setModuleName(int id, String name)
    {
        mModuleManager.setModuleName(id, name);
    }

    public String getModuleName(int id)
    {
        return mModuleManager.getModuleName(id);
    }

    public List<BaseModule> getAllModules()
    {
        return mModuleManager.getAllModuleList();
    }

    public int getModuleGroup(int id)
    {
        return mModuleManager.getModuleGroup(id);
    }

    // ---------------------------------------------------------------------------------------------------------
    // ------------ Configuration ------------------------------------------------------------------------------
    private RequestUrl mHttpUrl;

    private int mRequestMode;

    public void setModuleConfiguration(Configurations config) throws IllegalArgumentException
    {
        if (config == null)
        {
            throw new IllegalArgumentException("can not parse config!");
        }

        mRequestMode = config.getRequestMode();

        mHttpUrl = mRequestMode == RequestMode.MODE_LOCAL ? new LocalUrl(config.getLocalFeed()) : new HttpUrl(config.getHttpFeed());

        mModuleManager.setModuleConfiguration(config);
    }

    public RequestUrl getHttpUrl()
    {
        return mHttpUrl;
    }

    public int getRequestMode()
    {
        return mRequestMode;
    }

    // ---------------------------------------------------------------------------------------------------------
    // ------------ Demo status --------------------------------------------------------------------------------
    //public void setDemoMode(boolean demoMode)
    //    {
    //        mSharedPreferences.edit().putBoolean(Constants.KEY_SHARE_PREFERENCE_DEMO_MODE, demoMode).apply();
    //    }
    //
    //    public boolean isDemoMode()
    //    {
    //        return mSharedPreferences.getBoolean(Constants.KEY_SHARE_PREFERENCE_DEMO_MODE, isPhone());
    //    }
    //
    //    public void setDemoModeStatus(int status)
    //    {
    //        mSharedPreferences.edit().putInt(Constants.KEY_SHARE_PREFERENCE_DEMO_STATUS, status).apply();
    //    }
    //
    //    public int getDemoModeStatus()
    //    {
    //        return mSharedPreferences.getInt(Constants.KEY_SHARE_PREFERENCE_DEMO_STATUS, -1);
    //    }

    // ---------------------------------------------------------------------------------------------------------
    // ------------ Suggest ------------------------------------------------------------------------------------
    public int getEnergySuggestIndex()
    {
        final int index = mSharedPreferences.getInt(Constants.KEY_SHARE_PREFERENCE_SUGGEST, 0);

        final int nextIndex = (index + 1) % SuggestPagerAdapter.SUGGESTIONS.length;

        mSharedPreferences.edit().putInt(Constants.KEY_SHARE_PREFERENCE_SUGGEST, nextIndex).apply();

        return index;
    }
}
