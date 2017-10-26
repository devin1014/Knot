package com.android.smartlink.bean;

import com.android.smartlink.Constants;
import com.google.gson.annotations.SerializedName;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-26
 * Time: 16:45
 */
public class WeatherLocation
{
    @SerializedName("Location")
    private Location location;

    public String getLocation()
    {
        return location != null ? location.getName() : Constants.DEFAULT_LOCATION;
    }

    public static class Location
    {
        @SerializedName("EnglishName")
        private String name;

        public String getName()
        {
            return name;
        }
    }
}
