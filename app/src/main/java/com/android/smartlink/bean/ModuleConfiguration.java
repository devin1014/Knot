package com.android.smartlink.bean;

import java.util.List;

public class ModuleConfiguration
{
    private ModuleConfig monitor;
    private ModuleConfig control;

    public ModuleConfig getMonitor()
    {
        return monitor;
    }

    public ModuleConfig getControl()
    {
        return control;
    }

    public static class ModuleConfig
    {
        private String serverIP;
        private int port;
        private List<DataInfo> data;

        public String getServerIP()
        {
            return serverIP;
        }

        public int getPort()
        {
            return port;
        }

        public List<DataInfo> getData()
        {
            return data;
        }
    }

    public static class DataInfo
    {
        private int slaveID;
        private List<ModuleInfo> modules;

        public int getSlaveID()
        {
            return slaveID;
        }

        public List<ModuleInfo> getModules()
        {
            return modules;
        }
    }

    public static class ModuleInfo
    {
        private int id;
        private int channel;
        private String name;
        private int slaveID;

        public int getId()
        {
            return id;
        }

        public int getChannel()
        {
            return channel;
        }

        public String getName()
        {
            return name;
        }

        public int getSlaveID()
        {
            return slaveID;
        }

        public void setSlaveID(int slaveID)
        {
            this.slaveID = slaveID;
        }
    }
}
