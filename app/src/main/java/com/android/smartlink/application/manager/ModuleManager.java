package com.android.smartlink.application.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.smartlink.bean.AllFilter;
import com.android.smartlink.bean.Configurations;
import com.android.smartlink.bean.Configurations.DataInfo;
import com.android.smartlink.bean.Configurations.ModuleConfig;
import com.android.smartlink.bean.Configurations.ModuleInfo;
import com.android.smartlink.ui.model.BaseModule;
import com.android.smartlink.ui.model.BaseModule.DefaultBaseModuleImp;

import java.util.ArrayList;
import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-11-09
 * Time: 17:35
 */
class ModuleManager
{
    private static final String NAME = "Module:%s";

    private SharedPreferences mSharedPreferences;

    private List<BaseModule> mModuleList;

    private SparseArray<BaseModule> mModuleIds;
    private SparseArray<String> mModuleNames;
    private SparseIntArray mModuleGroups;

    ModuleManager(Application application)
    {
        mSharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

        mModuleList = new ArrayList<>();
        mModuleIds = new SparseArray<>();
        mModuleNames = new SparseArray<>();
        mModuleGroups = new SparseIntArray();
    }

    boolean isInitialized()
    {
        return mModuleIds != null && mModuleIds.size() > 0;
    }

    void setModuleConfiguration(Configurations configuration)
    {
        clear();

        parseModules(configuration.getMonitor());

        parseModules(configuration.getControl());

        DefaultBaseModuleImp allFilterModuleImp = new DefaultBaseModuleImp(new AllFilter());

        setModuleName(allFilterModuleImp.getId(), allFilterModuleImp.getName());
    }

    private void clear()
    {
        mModuleList.clear();
        mModuleIds.clear();
        mModuleNames.clear();
    }

    private void parseModules(ModuleConfig moduleConfig)
    {
        for (DataInfo e : moduleConfig.getData())
        {
            for (ModuleInfo info : e.getModules())
            {
                DefaultBaseModuleImp moduleImp = new DefaultBaseModuleImp(info);

                mModuleIds.put(moduleImp.getId(), moduleImp);

                mModuleList.add(moduleImp);

                mModuleGroups.put(moduleImp.getId(), info.getGroup());

                String name = innerGetModuleName(moduleImp.getId());

                if (TextUtils.isEmpty(name))
                {
                    setModuleName(moduleImp.getId(), info.getName());
                }
                else
                {
                    setModuleName(moduleImp.getId(), info.getName(), false);
                }
            }
        }
    }

    void setModuleName(int id, String name)
    {
        setModuleName(id, name, true);
    }

    private void setModuleName(int id, String name, boolean save)
    {
        mModuleNames.put(id, name);

        if (save)
        {
            mSharedPreferences.edit().putString(String.format(NAME, Integer.toHexString(id)), name).apply();
        }
    }

    String getModuleName(int id)
    {
        return mModuleNames.get(id);
    }

    int getModuleGroup(int id)
    {
        return mModuleGroups.get(id);
    }

    private String innerGetModuleName(int id)
    {
        return mSharedPreferences.getString(String.format(NAME, Integer.toHexString(id)), "");
    }

    List<BaseModule> getAllModuleList()
    {
        return mModuleList;
    }

    BaseModule getModule(int id)
    {
        return mModuleIds.get(id);
    }

}
