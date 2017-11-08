package com.android.smartlink.bean;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 11:02
 */
public class Events
{
    private List<Event> events;

    public List<Event> getEvents()
    {
        return events;
    }

    public static class Event
    {
        private int id;

        private int status;

        private String description;

        private long time;

        public int getId()
        {
            return id;
        }

        public int getStatus()
        {
            return status;
        }

        public String getDescription()
        {
            return description;
        }

        public long getTime()
        {
            return time;
        }
    }
}
