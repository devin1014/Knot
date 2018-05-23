package com.android.smartlink.util;

import android.content.Context;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-25
 * Time: 10:12
 */
public class HttpUrl
{
    private static final String TOMCAT_SRVER = "http://localhost:8080/examples/knot";

    public static String getHomeUrl()
    {
        return TOMCAT_SRVER + "/main/main_all.json";
    }

    public static String getEventsUrl()
    {
        return TOMCAT_SRVER + "/events/events_all.json";
    }

    public static String getPowerConsumeUrl(int id)
    {
        return TOMCAT_SRVER + "/main/30DayEnergy_" + id + ".json";
    }

    public static String getAccuWeatherUrl(Context context)
    {
        return "https://api.accuweather.com/localweather/v1/106577?apikey=" + Utils.getApplicationMetaData(context, "accuWeatherKey");
    }

    //https://free-api.heweather.com/v5/weather?city=shanghai&key=7f70fe2b7604408bafba86681d9ddcd4
    public static String getWeatherUrl(Context context, String city)
    {
        return "https://free-api.heweather.com/v5/weather?city=" + city + "&key=" + Utils.getApplicationMetaData(context, "weatherKey");
    }

}
