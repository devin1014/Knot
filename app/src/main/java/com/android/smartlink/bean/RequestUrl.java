package com.android.smartlink.bean;

import android.content.Context;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Configurations.FeedUrl;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.Utils;

public abstract class RequestUrl
{
    String mLocationUrl;
    String mWeatherUrl;
    String mMainDataUrl;
    String mEventsUrl;
    String mEnergyUrl;

    private String getLocationUrl()
    {
        return mLocationUrl;
    }

    private String getWeatherUrl(String city)
    {
        return mWeatherUrl.replace("${city}", city);
    }

    private String getMainDataUrl()
    {
        return mMainDataUrl;
    }

    private String getEventsUrl()
    {
        return mEventsUrl;
    }

    private String getEnergyUrl(int id)
    {
        return mEnergyUrl.replace("${id}", String.valueOf(id));
    }

    public static class HttpUrl extends RequestUrl
    {
        @SuppressWarnings("ConstantConditions")
        public HttpUrl(FeedUrl feedUrl)
        {
            Context context = AppManager.getInstance().getApplication();

            mLocationUrl = feedUrl.getWeatherLocalUrl()
                    .replace("${weatherLocalKey}", Utils.getApplicationMetaData(context, "weatherLocalKey"));

            mWeatherUrl = feedUrl.getWeatherUrl()
                    .replace("${weatherKey}", Utils.getApplicationMetaData(context, "weatherKey"));

            mMainDataUrl = feedUrl.getMainDataUrl();

            mEventsUrl = feedUrl.getEventsUrl();

            mEnergyUrl = feedUrl.getEnergyUrl();
        }
    }

    public static class LocalUrl extends RequestUrl
    {
        public LocalUrl(FeedUrl feedUrl)
        {
            int status = -1;

            mLocationUrl = feedUrl.getWeatherLocalUrl();

            mWeatherUrl = feedUrl.getWeatherUrl();

            mMainDataUrl = feedUrl.getMainDataUrl().replace("${state}", ConvertUtil.convertStatus(status));

            mEventsUrl = feedUrl.getEventsUrl().replace("${state}", ConvertUtil.convertStatus(status));

            mEnergyUrl = feedUrl.getEnergyUrl();
        }
    }

    // ---------------------------------------------------------------------------------------------------------
    // ------------ Helper -------------------------------------------------------------------------------------
    public static String obtainLocationUrl()
    {
        return AppManager.getInstance().getHttpUrl().getLocationUrl();
    }

    public static String obtainWeatherUrl(String city)
    {
        return AppManager.getInstance().getHttpUrl().getWeatherUrl(city);
    }

    public static String obtainMainDataUrl()
    {
        return AppManager.getInstance().getHttpUrl().getMainDataUrl();
    }

    public static String obtainEventsUrl()
    {
        return AppManager.getInstance().getHttpUrl().getEventsUrl();
    }

    public static String obtainEnergyUrl(int id)
    {
        return AppManager.getInstance().getHttpUrl().getEnergyUrl(id);
    }
}
