package com.android.smartlink.assist;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.util.HttpUrl;
import com.android.smartlink.util.LogUtil;
import com.google.gson.Gson;

import java.io.InputStreamReader;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:39
 */
public class InitializeTask extends AsyncTask<Void, Void, Boolean> implements RequestCallback<Weather>
{
    private AssetManager mAssetManager;

    private WeatherRequestProvider mWeatherRequestProvider;

    private InitializeTaskCallback mTaskCallback;

    private LocationManager mLocationManager;

    private double[] mLocations;

    public InitializeTask(Context context, InitializeTaskCallback callback)
    {
        mAssetManager = context.getAssets();

        mTaskCallback = callback;

        mWeatherRequestProvider = new WeatherRequestProvider(this);
    }

    @Override
    protected void onPreExecute()
    {
        final double[] locations = new double[2];

        Context context = AppManager.getInstance().getApplication();

        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                ContextCompat.checkSelfPermission(context, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null)
            {
                locations[0] = location.getLatitude();

                locations[1] = location.getLongitude();

                mLocations = locations;

                notifyToNext();

                return;
            }
        }

        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null)
        {
            locations[0] = location.getLatitude(); //经度

            locations[1] = location.getLongitude(); //纬度

            mLocations = locations;

            notifyToNext();
        }
        else
        {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, mLocationListener);
        }
    }

    private LocationListener mLocationListener = new LocationListener()
    {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
        }

        @Override
        public void onProviderEnabled(String provider)
        {
        }

        @Override
        public void onProviderDisabled(String provider)
        {
        }

        @Override
        public void onLocationChanged(Location location)
        {
            final double[] locations = new double[2];

            locations[0] = location.getLatitude(); //经度

            locations[1] = location.getLongitude(); //纬度

            mLocations = locations;

            LogUtil.log(this, location.toString());

            notifyToNext();
        }
    };

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        if (mAssetManager == null)
        {
            return false;
        }

        Gson gson = new Gson();

        try
        {
            Equipments equipments = gson.fromJson(new InputStreamReader(mAssetManager.open("equipment.json")), Equipments.class);

            AppManager.getInstance().setEquipments(equipments);

            synchronized (this)
            {
                if (mLocations == null)
                {
                    wait();
                }
            }

            mLocationManager.removeUpdates(mLocationListener);

            mWeatherRequestProvider.request(HttpUrl.getWeatherUrl(AppManager.getInstance().getApplication(), mLocations));

            synchronized (this)
            {
                if (!mWeatherResponse)
                {
                    wait(30 * 1000); // max 30s.
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        if (mTaskCallback != null)
        {
            mTaskCallback.onInitialized(result != null && result);
        }
    }

    public void destroy()
    {
        mTaskCallback = null;

        mAssetManager = null;

        cancel(true);
    }

    private boolean mWeatherResponse = false;

    @Override
    public void onResponse(Weather weather)
    {
        AppManager.getInstance().setWeather(weather);

        mWeatherResponse = true;

        notifyToNext();
    }

    @Override
    public void onError(Throwable throwable)
    {
        mWeatherResponse = true;

        notifyToNext();
    }

    private void notifyToNext()
    {
        try
        {
            synchronized (this)
            {
                notify();
            }
        }
        catch (Exception ignored)
        {
        }
    }

    public interface InitializeTaskCallback
    {
        void onInitialized(boolean success);
    }
}
