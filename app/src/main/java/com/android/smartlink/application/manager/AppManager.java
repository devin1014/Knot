package com.android.smartlink.application.manager;

import android.annotation.SuppressLint;
import android.app.Application;

import com.android.smartlink.R;

import java.util.Calendar;

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

    private final Application mApplication;

    private AppManager(Application application)
    {
        mApplication = application;
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
}
