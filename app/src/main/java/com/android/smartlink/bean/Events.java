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

        private String startTime;

        private long startTimeStamp;

        private String endTime;

        private long endTimeStamp;

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

        public String getStartTime()
        {
            return startTime;
        }

        public long getStartTimeStamp()
        {
            return startTimeStamp;
        }

        public String getEndTime()
        {
            return endTime;
        }

        public long getEndTimeStamp()
        {
            return endTimeStamp;
        }
    }
}
