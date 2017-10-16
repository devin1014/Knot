package com.android.smartlink.bean;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:22
 */
public class Equipments
{
    private List<Equipment> equipments;

    public List<Equipment> getEquipments()
    {
        return equipments;
    }

    public static class Equipment
    {
        private int id;

        private String name;

        private String icon;
    }
}
