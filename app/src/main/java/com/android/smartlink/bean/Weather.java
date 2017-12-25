package com.android.smartlink.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 14:14
 */
public class Weather implements Serializable
{
    private static final long serialVersionUID = -9191813124257461068L;

    private String status;

    private WeatherAQI aqi;

    private WeatherBasic basic;

    private List<WeatherDailyForecast> daily_forecast;

    private List<WeatherHourlyForecast> hourly_forecast;

    private WeatherNow now;

    public String getStatus()
    {
        return status;
    }

    public WeatherAQI getAqi()
    {
        return aqi;
    }

    public WeatherBasic getBasic()
    {
        return basic;
    }

    public List<WeatherDailyForecast> getDaily_forecast()
    {
        return daily_forecast;
    }

    public List<WeatherHourlyForecast> getHourly_forecast()
    {
        return hourly_forecast;
    }

    public WeatherNow getNow()
    {
        return now;
    }

    public static class WeatherAQI implements Serializable
    {
        private static final long serialVersionUID = 3954653136876598403L;

        private WeatherCity city;

        public int getAqi()
        {
            return city != null ? city.getAqi() : -1;
        }

        public String getQlty()
        {
            return city != null ? city.getQlty() : null;
        }

        public int getPm25()
        {
            return city != null ? city.getPm25() : 0;
        }

        public static class WeatherCity implements Serializable
        {
            private static final long serialVersionUID = 2714198007357304680L;

            private int aqi;

            private String qlty; //空气质量

            private int pm25;

            public int getAqi()
            {
                return aqi;
            }

            public String getQlty()
            {
                return qlty;
            }

            public int getPm25()
            {
                return pm25;
            }
        }
    }

    public static class WeatherBasic implements Serializable
    {
        private static final long serialVersionUID = 2577332668059638893L;

        private String city;

        private String cnty; //国家

        private String id; //CN101020100 城市ID

        private String lat; // 纬度

        private String lon; // 经度

        private WeatherUpdate update; //更新时间

        private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        public String getCity()
        {
            return city;
        }

        public String getCountry()
        {
            return cnty;
        }

        public String getId()
        {
            return id;
        }

        public String getLat()
        {
            return lat;
        }

        public String getLon()
        {
            return lon;
        }

        public Date getUpdateTime()
        {
            mDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

            if (update != null)
            {
                try
                {
                    return mDateFormat.parse(update.getLoc());
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }

            return new Date();
        }

        public static class WeatherUpdate implements Serializable
        {
            private static final long serialVersionUID = -713437170643312166L;

            private String loc; //中国时区，更新时间

            public String getLoc()
            {
                return loc;
            }
        }
    }

    public static class WeatherDailyForecast implements Serializable
    {
        private static final long serialVersionUID = -1257369001628013988L;

        private String date;

        private WeatherTemperature tmp;

        private WeatherCond cond;

        public String getDate()
        {
            return date;
        }

        public int getMax()
        {
            return tmp != null ? tmp.getMax() : 0;
        }

        public int getMin()
        {
            return tmp != null ? tmp.getMin() : 0;
        }

        public WeatherCond getCond()
        {
            return cond;
        }

        public static class WeatherTemperature implements Serializable
        {
            private static final long serialVersionUID = -1558673287444981447L;

            private int max;

            private int min;

            public int getMax()
            {
                return max;
            }

            public int getMin()
            {
                return min;
            }
        }
    }

    public static class WeatherHourlyForecast implements Serializable
    {
        private static final long serialVersionUID = 6917038218545235987L;

        private WeatherCond cond; //天气

        private String date;

        private int tmp;

        public WeatherCond getCond()
        {
            return cond;
        }

        public String getDate()
        {
            return date;
        }

        public int getTmp()
        {
            return tmp;
        }
    }

    public static class WeatherNow implements Serializable
    {
        private static final long serialVersionUID = -2371665711992306110L;

        private int tmp;

        private WeatherCond cond;

        public int getTmp()
        {
            return tmp;
        }

        public WeatherCond getCond()
        {
            return cond;
        }
    }

    public static class WeatherCond implements Serializable
    {
        private static final long serialVersionUID = -4899883461999454249L;

        private int code;

        private int code_d; //白天

        private int code_n; //晚上

        private String txt;

        private String txt_d;

        private String txt_n;

        public int getCode()
        {
            return code;
        }

        public String getTxt()
        {
            return txt;
        }

        public int getCode_d()
        {
            return code_d;
        }

        public int getCode_n()
        {
            return code_n;
        }

        public String getTxt_d()
        {
            return txt_d;
        }

        public String getTxt_n()
        {
            return txt_n;
        }
    }
}
