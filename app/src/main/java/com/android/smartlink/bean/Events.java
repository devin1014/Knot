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

        private String date;

        private String description;

        private String startTime;

        private String endTime;

        public int getId()
        {
            return id;
        }

        public String getDate()
        {
            return date;
        }

        public String getDescription()
        {
            return description;
        }

        public String getStartTime()
        {
            return startTime;
        }

        public String getEndTime()
        {
            return endTime;
        }
    }
}
