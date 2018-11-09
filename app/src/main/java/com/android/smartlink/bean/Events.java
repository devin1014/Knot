package com.android.smartlink.bean;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * User: LIUWEI
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

    public static class Event implements Comparable<Event>
    {
        private int id;

        private int slaveID;

        private int channel;

        private int status;

        private String description;

        private long time;

        public void setId(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return id;
        }

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

        public String getDescription()
        {
            return description;
        }

        public long getTime()
        {
            return time;
        }

        @Override
        public int compareTo(@NonNull Event event)
        {
            if (status == event.getStatus())
            {
                return 0;
            }

            if (status == 1)
            {
                return -1;
            }

            if (event.getStatus() == 1)
            {
                return 1;
            }

            return 0;
        }
    }
}
