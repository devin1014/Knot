package com.android.smartlink.bean;

import java.io.Serializable;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 14:14
 */
public class Weather implements Serializable
{
    private static final long serialVersionUID = -9191813124257461068L;

    private String date; //20171018

    private String message;

    private int status;

    private String city;

    private int count;

    private Data data;

    public Data getData()
    {
        return data;
    }

    public String getDate()
    {
        return date;
    }

    public String getMessage()
    {
        return message;
    }

    public int getStatus()
    {
        return status;
    }

    public String getCity()
    {
        return city;
    }

    public static class Data implements Serializable
    {
        private static final long serialVersionUID = 9173903554919085401L;

        private String shidu;

        private String pm25;

        private String pm10;

        private String quality;

        private int wendu;

        private List<Forecast> forecast;

        public String getShidu()
        {
            return shidu;
        }

        public String getPm25()
        {
            return pm25;
        }

        public String getPm10()
        {
            return pm10;
        }

        public String getQuality()
        {
            return quality;
        }

        public int getWendu()
        {
            return wendu;
        }

        public List<Forecast> getForecast()
        {
            return forecast;
        }
    }

    public static class Forecast implements Serializable
    {
        private static final long serialVersionUID = 5524245576038924754L;

        private String date;

        private String high;

        private String low;

        private int aqi;

        private String type;

        public String getDate()
        {
            return date;
        }

        public String getHigh()
        {
            return high;
        }

        public String getLow()
        {
            return low;
        }

        public int getAqi()
        {
            return aqi;
        }

        public String getType()
        {
            return type;
        }
    }
}
