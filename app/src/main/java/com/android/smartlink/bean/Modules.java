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

        private float power;

        private float powerFactor;

        private float current;

        private float voltage;

        private float energy;

        private float dianliang;

        private float fuzai;

        public int getId()
        {
            return id;
        }

        public float getPower()
        {
            return power;
        }

        public int getStatus()
        {
            return status;
        }

        public float getPowerFactor()
        {
            return powerFactor;
        }

        public float getCurrent()
        {
            return current;
        }

        public float getVoltage()
        {
            return voltage;
        }

        public float getEnergy()
        {
            return energy;
        }

        public float getDianliang()
        {
            return dianliang;
        }

        public float getFuzai()
        {
            return fuzai;
        }
    }
}
