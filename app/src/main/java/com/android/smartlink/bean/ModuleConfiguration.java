package com.android.smartlink.bean;

import java.util.List;

public class ModuleConfiguration
{
    private int requestMode;
    private FeedUrl localFeed;
    private FeedUrl httpFeed;
    private ModuleConfig monitor;
    private ModuleConfig control;

    public int getRequestMode()
    {
        return requestMode;
    }

    public FeedUrl getLocalFeed()
    {
        return localFeed;
    }

    public FeedUrl getHttpFeed()
    {
        return httpFeed;
    }

    public ModuleConfig getMonitor()
    {
        return monitor;
    }

    public ModuleConfig getControl()
    {
        return control;
    }

    public static class FeedUrl
    {
        private String weatherLocalUrl;
        private String weatherUrl;
        private String mainDataUrl;
        private String eventsUrl;
        private String energyUrl;

        public String getWeatherLocalUrl()
        {
            return weatherLocalUrl;
        }

        public String getWeatherUrl()
        {
            return weatherUrl;
        }

        public String getMainDataUrl()
        {
            return mainDataUrl;
        }

        public String getEventsUrl()
        {
            return eventsUrl;
        }

        public String getEnergyUrl()
        {
            return energyUrl;
        }
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
