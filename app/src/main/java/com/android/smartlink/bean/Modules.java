package com.android.smartlink.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:22
 */
public class Modules
{
    @SerializedName("data")
    private List<Module> modules;

    public List<Module> getModules()
    {
        return modules;
    }

    public static class Module implements Serializable
    {
        private static final long serialVersionUID = -4975045285069888629L;

        @SerializedName("slaveID")
        private int id;

        @SerializedName("alarm")
        private int status;

        //private float power;

        //private float powerFactor;

        private String current;

        //private float voltage;

        private String energy;

        public int getId()
        {
            return id;
        }

        public int getStatus()
        {
            return status;
        }

        public String getCurrent()
        {
            return current;
        }

        public String getEnergy()
        {
            return energy;
        }
    }
}
