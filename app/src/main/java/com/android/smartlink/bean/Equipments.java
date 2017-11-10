package com.android.smartlink.bean;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:22
 */
public class Equipments
{
    private int version;

    private List<Equipment> equipments;

    public int getVersion()
    {
        return version;
    }

    public List<Equipment> getEquipments()
    {
        return equipments;
    }

    public static class Equipment
    {
        private int id;

        private String name;

        private String icon;

        private String icon_light;

        public int getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }

        public String getIcon()
        {
            return icon;
        }

        public String getLightIcon()
        {
            return icon_light;
        }
    }
}
