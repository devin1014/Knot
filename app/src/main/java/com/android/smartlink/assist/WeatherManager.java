package com.android.smartlink.assist;

import android.app.Activity;
import android.text.TextUtils;

import com.android.smartlink.Constants;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.bean.WeatherLocation;
import com.android.smartlink.util.HttpUrl;

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

    private boolean mDestroy = false;

    public WeatherManager(Activity activity, WeatherCallback callback)
    {
        mDestroy = false;

        mCallback = callback;

        mWeatherProvider = WeatherProvider.newInstance(activity, mWeatherCallback);

        mLocationProvider = LocationProvider.newInstance(activity, mWeatherLocationCallback);
    }

    public void requestWeather()
    {
        String location = AppManager.getInstance().getLocation();

        if (TextUtils.isEmpty(location))
        {
            mLocationProvider.request(HttpUrl.getAccuWeatherUrl(mLocationProvider.getActivity()));

            mWeatherProvider.request("http://test.xxxx");//todo
        }
        else
        {
            mWeatherProvider.request(HttpUrl.getWeatherUrl(mWeatherProvider.getActivity(), location));
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

                mWeatherProvider.request(HttpUrl.getWeatherUrl(mWeatherProvider.getActivity(), location.getLocation()));
            }
        }

        @Override
        public void onError(Throwable throwable)
        {
            if (!mDestroy)
            {
                mWeatherProvider.request(HttpUrl.getWeatherUrl(mWeatherProvider.getActivity(), Constants.DEFAULT_LOCATION));
            }
        }
    };
}
