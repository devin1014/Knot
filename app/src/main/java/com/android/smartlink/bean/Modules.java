package com.android.smartlink.bean;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:22
 */
public class Modules
{
    private List<Module> modules;

    public List<Module> getModules()
    {
        return modules;
    }

    public static class Module
    {
        private int id;

        private String power;

        private int status;

        private Health health;

        private String temperature;

        public int getId()
        {
            return id;
        }

        public String getPower()
        {
            return power;
        }

        public int getStatus()
        {
            return status;
        }

        public Health getHealth()
        {
            return health;
        }

        public String getTemperature()
        {
            return temperature;
        }
    }
}
