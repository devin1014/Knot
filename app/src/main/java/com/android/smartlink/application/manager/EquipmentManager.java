package com.android.smartlink.application.manager;

import android.annotation.SuppressLint;
import android.app.Application;

import com.android.smartlink.bean.Equipments;
import com.android.smartlink.bean.Equipments.Equipment;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:47
 */
public class EquipmentManager
{
    @SuppressLint("StaticFieldLeak")
    private static EquipmentManager sEquipmentManager;

    public static EquipmentManager getInstance()
    {
        return sEquipmentManager;
    }

    public static void initialize(Application application)
    {
        if (sEquipmentManager == null)
        {
            sEquipmentManager = new EquipmentManager(application);
        }
    }

    private final Application mApplication;

    private List<Equipment> mEquipments;

    private EquipmentManager(Application application)
    {
        mApplication = application;
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
    }
}
