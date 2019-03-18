package com.android.smartlink.bean;

import com.android.smartlink.ui.model.BaseModule;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:22
 */
public class ModulesData implements Serializable
{
    @SerializedName("data")
    private List<MonitorModuleData> modules;

    @SerializedName("data255")
    private List<ToggleModuleData> toggles255;

    @SerializedName("data1")
    private List<ToggleModuleData> toggles1;

    public List<MonitorModuleData> getMonitorModules()
    {
        return modules;
    }

    private List<ToggleModuleData> mToggles;

    public List<ToggleModuleData> getToggleModules()
    {
        if (mToggles == null)
        {
            List<ToggleModuleData> list = new ArrayList<>();
            if (toggles255 != null)
            {
                list.addAll(toggles255);
            }
            if (toggles1 != null)
            {
                list.addAll(toggles1);
            }
            mToggles = list;
        }

        return mToggles;
    }

    public static class MonitorModuleData implements Serializable, BaseModule
    {
        private static final long serialVersionUID = -4975045285069888629L;

        private int slaveID;

        @SerializedName("alarm")
        private int status;

        private String current1;

        private String current2;

        private String current3;

        private String voltage1;

        private String voltage2;

        private String voltage3;

        private String power;

        private String powerFactor;

        private String energy;

        public int getStatus()
        {
            return status;
        }

        public String getCurrent1()
        {
            return current1;
        }

        public String getCurrent2()
        {
            return current2;
        }

        public String getCurrent3()
        {
            return current3;
        }

        public String getVoltage1()
        {
            return voltage1;
        }

        public String getVoltage2()
        {
            return voltage2;
        }

        public String getVoltage3()
        {
            return voltage3;
        }

        public String getPower()
        {
            return power;
        }

        public String getPowerFactor()
        {
            return powerFactor;
        }

        public String getEnergy()
        {
            return energy;
        }

        @Override
        public int getSlaveID()
        {
            return slaveID;
        }

        @Override
        public int getChannel()
        {
            return 0;
        }

        @Override
        public String getName()
        {
            return null;
        }
    }

    public static class ToggleModuleData implements Serializable, BaseModule
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

        @Override
        public String getName()
        {
            return null;
        }
    }
}
