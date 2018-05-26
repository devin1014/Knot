package com.android.smartlink.bean;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:22
 */
public class Modbus
{
    private int version;

    private String serverIP;

    private int port;

    private int[] modules;

    private List<Equipment> equipments;

    private List<EquipmentToggle> toggles;

    public int getVersion()
    {
        return version;
    }

    public List<Equipment> getEquipments()
    {
        return equipments;
    }

    public String getServerIP()
    {
        return serverIP;
    }

    public int getPort()
    {
        return port;
    }

    public int[] getModules()
    {
        return modules;
    }

    public List<EquipmentToggle> getToggles()
    {
        return toggles;
    }

    // --------------------------------------------------------------
    // Modules
    // --------------------------------------------------------------
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

    // --------------------------------------------------------------
    // Toggle
    // --------------------------------------------------------------
    public static class EquipmentToggle
    {
        private int id;

        private int slaveID;

        private int channel;

        private String name;

        private String icon;

        public int getChannel()
        {
            return channel;
        }

        public int getSlaveID()
        {
            return slaveID;
        }

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
    }
}
