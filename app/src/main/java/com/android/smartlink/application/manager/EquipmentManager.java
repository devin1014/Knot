package com.android.smartlink.application.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.SparseArray;

import com.android.smartlink.bean.Modbus;
import com.android.smartlink.bean.Modbus.Equipment;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-09
 * Time: 17:35
 */
class EquipmentManager
{
    private static final String VERSION = "com.android.smartlink.application.manager.EquipmentManager.version";

    private static final String NAME = "com.android.smartlink.application.manager.EquipmentManager.name";

    private SharedPreferences mSharedPreferences;

    private SparseArray<Equipment> mSparseArray;

    private List<Equipment> mEquipments;

    private Equipment mEmpty = new Equipment();

    EquipmentManager(Application application)
    {
        mSharedPreferences = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);

        mSparseArray = new SparseArray<>();
    }

    boolean isInitialized()
    {
        return mEquipments != null && mEquipments.size() > 0;
    }

    void setEquipments(Modbus modbus)
    {
        mEquipments = modbus.getEquipments();

        if (modbus.getVersion() > getVersion())
        {
            updateDatabase(modbus.getVersion(), modbus.getEquipments());
        }

        for (Equipment e : modbus.getEquipments())
        {
            mSparseArray.put(e.getId(), e);
        }
    }

    private int getVersion()
    {
        return mSharedPreferences.getInt(VERSION, 0);
    }

    private void updateDatabase(int version, List<Equipment> list)
    {
        Editor editor = mSharedPreferences.edit();

        editor.putInt(VERSION, version);

        for (Equipment e : list)
        {
            editor.putString(NAME + e.getId(), e.getName());
        }

        editor.apply();
    }

    void update(int id, String name)
    {
        mSharedPreferences.edit().putString(NAME + id, name).apply();
    }

    // ------------ get ----------------------

    List<Equipment> getEquipments()
    {
        return mEquipments;
    }

    Equipment getEquipment(int id)
    {
        Equipment equipment = mSparseArray.get(id);

        return equipment != null ? equipment : mEmpty;
    }

    String getName(int id)
    {
        return mSharedPreferences.getString(NAME + id, getEquipment(id).getName());
    }
}
