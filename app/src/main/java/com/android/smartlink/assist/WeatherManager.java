package com.android.smartlink.assist;

import android.text.TextUtils;

import com.android.smartlink.Constants;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.bean.WeatherLocation;

/**
 * User: LIUWEI
 * Date: 2017-12-25
 * Time: 11:49
 */
public class WeatherManager
{
    private WeatherProvider mWeatherProvider;

    private LocationProvider mLocationProvider;

    private WeatherCallback mCallback;

    private boolean mDestroy;

    public WeatherManager(WeatherCallback callback)
    {
        mDestroy = false;

        mCallback = callback;

        mWeatherProvider = WeatherProvider.newInstance(mWeatherCallback);

        mLocationProvider = LocationProvider.newInstance(mWeatherLocationCallback);
    }

    public void requestWeather()
    {
        String location = AppManager.getInstance().getLocation();

        if (TextUtils.isEmpty(location))
        {
            mLocationProvider.request(AppManager.getInstance().getHttpUrl().getLocationUrl());
        }
        else
        {
            mWeatherProvider.request(AppManager.getInstance().getHttpUrl().getWeatherUrl(location));
        }
    }

    public void destroy()
    {
        mDestroy = true;

        mCallback = null;

        mLocationProvider.destroy();

        mWeatherProvider.destroy();
    }

    public interface WeatherCallback
    {
        void onWeatherResponse(Weather weather);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private RequestCallback<Weather> mWeatherCallback = new RequestCallback<Weather>()
    {
        @Override
        public void onResponse(Weather weather)
        {
            if (mCallback != null)
            {
                mCallback.onWeatherResponse(weather);
            }
        }

        @Override
        public void onError(Throwable throwable)
        {
        }
    };

    @SuppressWarnings("FieldCanBeLocal")
    private RequestCallback<WeatherLocation> mWeatherLocationCallback = new RequestCallback<WeatherLocation>()
    {
        @Override
        public void onResponse(WeatherLocation location)
        {
            if (!mDestroy)
            {
                AppManager.getInstance().setLocation(location.getLocation());

                mWeatherProvider.request(AppManager.getInstance().getHttpUrl().getWeatherUrl(location.getLocation()));
            }
        }

        @Override
        public void onError(Throwable throwable)
        {
            if (!mDestroy)
            {
                mWeatherProvider.request(AppManager.getInstance().getHttpUrl().getWeatherUrl(Constants.DEFAULT_LOCATION));
            }
        }
    };
}
