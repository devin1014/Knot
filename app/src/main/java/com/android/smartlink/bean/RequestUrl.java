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

    public String getLocationUrl()
    {
        return mLocationUrl;
    }

    public String getWeatherUrl(String city)
    {
        return mWeatherUrl.replace("${city}", city);
    }

    public String getMainDataUrl()
    {
        return mMainDataUrl;
    }

    public String getEventsUrl()
    {
        return mEventsUrl;
    }

    public String getEnergyUrl(int id)
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
            //Context context = AppManager.getInstance().getApplication();

            int status = AppManager.getInstance().getDemoModeStatus();

            mLocationUrl = feedUrl.getWeatherLocalUrl();

            mWeatherUrl = feedUrl.getWeatherUrl();

            mMainDataUrl = feedUrl.getMainDataUrl().replace("${state}", ConvertUtil.convertStatus(status));

            mEventsUrl = feedUrl.getEventsUrl().replace("${state}", ConvertUtil.convertStatus(status));

            mEnergyUrl = feedUrl.getEnergyUrl();
        }
    }
}
