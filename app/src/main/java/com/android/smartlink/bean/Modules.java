package com.android.smartlink.bean;

import java.io.Serializable;
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

    public static class Module implements Serializable
    {
        private static final long serialVersionUID = -4975045285069888629L;

        private int id;

        private String power;

        private int status;

        private int power_load;

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

        public int getPowerLoad()
        {
            return power_load;
        }
    }
}
