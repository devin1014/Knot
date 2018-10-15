package com.android.smartlink.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:22
 */
public class Modules implements Serializable
{
    @SerializedName("data")
    private List<Module> modules;

    @SerializedName("data255")
    private List<Toggle> toggles255;

    @SerializedName("data1")
    private List<Toggle> toggles1;

    public List<Module> getModules()
    {
        return modules;
    }

    public List<Toggle> getToggles()
    {
        List<Toggle> list = new ArrayList<>();
        list.addAll(toggles255);
        list.addAll(toggles1);
        return list;
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

    public static class Toggle implements Serializable
    {
        private static final long serialVersionUID = -5677078707406604424L;

        private int slaveID;

        private int channel;

        private int status;

        public int getSlaveID()
        {
            return slaveID;
        }

        public int getChannel()
        {
            return channel;
        }

        public int getStatus()
        {
            return status;
        }
    }
}
