package com.android.smartlink.application.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.SparseArray;

import com.android.smartlink.bean.ModuleConfiguration;
import com.android.smartlink.bean.ModuleConfiguration.DataInfo;
import com.android.smartlink.bean.ModuleConfiguration.ModuleConfig;
import com.android.smartlink.bean.ModuleConfiguration.ModuleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-11-09
 * Time: 17:35
 */
class ModuleManager
{
    private static final String NAME = "Module:";

    private SharedPreferences mSharedPreferences;

    private List<ModuleInfo> mModuleList;
    private SparseArray<List<ModuleInfo>> mModuleTypes; //slaveId=0,1,255
    private SparseArray<ModuleInfo> mModuleIds;
    private SparseArray<String> mModuleNames;

    ModuleManager(Application application)
    {
        mSharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

        mModuleList = new ArrayList<>();
        mModuleTypes = new SparseArray<>();
        mModuleIds = new SparseArray<>();
        mModuleNames = new SparseArray<>();
    }

    boolean isInitialized()
    {
        return mModuleTypes != null && mModuleTypes.size() > 0 && mModuleIds != null && mModuleIds.size() > 0;
    }

    void setModuleConfiguration(ModuleConfiguration configuration)
    {
        parseModules(configuration.getMonitor());

        parseModules(configuration.getControl());
    }

    private void parseModules(ModuleConfig moduleConfig)
    {
        for (DataInfo e : moduleConfig.getData())
        {
            List<ModuleInfo> list = mModuleTypes.get(e.getSlaveID());

            if (list == null)
            {
                list = new ArrayList<>();

                mModuleTypes.put(e.getSlaveID(), list);
            }

            for (ModuleInfo info : e.getModules())
            {
                info.setSlaveID(e.getSlaveID());

                list.add(info);

                mModuleIds.put(info.getId(), info);

                mModuleIds.put(info.getChannel(), info);

                mModuleList.add(info);

                String name = innerGetModuleName(info.getId());

                if (TextUtils.isEmpty(name))
                {
                    setModuleName(info.getId(), info.getName());
                }
                else
                {
                    mModuleNames.put(info.getId(), name);
                    mModuleNames.put(info.getChannel(), name);
                }
            }
        }
    }

    void setModuleName(int id, String name)
    {
        mSharedPreferences.edit().putString(NAME + id, name).apply();

        mModuleNames.put(id, name);
    }

    String getModuleName(int id)
    {
        return mModuleNames.get(id);
    }

    private String innerGetModuleName(int id)
    {
        return mSharedPreferences.getString(NAME + id, "");
    }

    List<ModuleInfo> getAllModuleList()
    {
        return mModuleList;
    }

    List<ModuleInfo> getModuleList(int slaveId)
    {
        return mModuleTypes.get(slaveId);
    }

    ModuleInfo getModule(int id)
    {
        return mModuleIds.get(id);
    }

}
