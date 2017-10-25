package com.android.smartlink.assist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.util.HttpUrl;
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

    private String mUrl;

    public InitializeTask(Context context, InitializeTaskCallback callback)
    {
        mAssetManager = context.getAssets();

        mTaskCallback = callback;

        mUrl = HttpUrl.getWeatherUrl(context, "上海");

        mWeatherRequestProvider = new WeatherRequestProvider(this);
    }

    @Override
    protected void onPreExecute()
    {
        mWeatherRequestProvider.request(mUrl);
    }

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

            if (!mWeatherResponse)
            {
                synchronized (this)
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

    @Override
    public void onError(Throwable throwable)
    {
        mWeatherResponse = true;

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
