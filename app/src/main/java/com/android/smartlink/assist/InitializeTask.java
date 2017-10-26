package com.android.smartlink.assist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;

import com.android.smartlink.Constants;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.bean.WeatherLocation;
import com.android.smartlink.util.HttpUrl;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.request.GetRequest;

import org.json.JSONObject;

import java.io.InputStreamReader;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 14:39
 */
public class InitializeTask extends AsyncTask<Void, Void, Boolean>
{
    private AssetManager mAssetManager;

    private InitializeTaskCallback mTaskCallback;

    public interface InitializeTaskCallback
    {
        void onInitialized(boolean success);
    }

    public InitializeTask(Context context, InitializeTaskCallback callback)
    {
        mAssetManager = context.getAssets();

        mTaskCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids)
    {
        if (mAssetManager == null)
        {
            return false;
        }

        final long timeStamp = SystemClock.uptimeMillis();

        Gson gson = new Gson();

        try
        {
            Equipments equipments = gson.fromJson(new InputStreamReader(mAssetManager.open("equipment.json")), Equipments.class);

            AppManager.getInstance().setEquipments(equipments);

            if (!AppManager.getInstance().checkWeather())
            {
                String location = AppManager.getInstance().getLocation();

                if (TextUtils.isEmpty(location))
                {
                    WeatherLocation weatherLocation = getLocation();

                    if (weatherLocation != null)
                    {
                        location = weatherLocation.getLocation();

                        AppManager.getInstance().setLocation(location);
                    }
                    else
                    {
                        location = Constants.DEFAULT_LOCATION;
                    }
                }

                Weather weather = getWeather(location);

                AppManager.getInstance().setWeather(weather);
            }

            if (SystemClock.uptimeMillis() - timeStamp < 1500)
            {
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    private WeatherLocation getLocation() throws Exception
    {
        GetRequest<WeatherLocation> request = OkGo.get(HttpUrl.getAccuWeatherUrl(AppManager.getInstance().getApplication()));

        request.converter(new Converter<WeatherLocation>()
        {
            @Override
            public WeatherLocation convertResponse(okhttp3.Response response) throws Throwable
            {
                if (response.code() == 200)
                {
                    //noinspection ConstantConditions
                    String result = response.body().string();

                    return new Gson().fromJson(result, WeatherLocation.class);
                }

                return null;
            }
        });

        return request.adapt().execute().body();
    }

    private Weather getWeather(String city) throws Exception
    {
        GetRequest<Weather> request = OkGo.get(HttpUrl.getWeatherUrl(AppManager.getInstance().getApplication(), city));

        request.converter(new Converter<Weather>()
        {
            @Override
            public Weather convertResponse(okhttp3.Response response) throws Throwable
            {
                if (response.code() == 200 && response.body() != null)
                {
                    @SuppressWarnings("ConstantConditions")
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    JSONObject weather = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);

                    return new Gson().fromJson(weather.toString(), Weather.class);
                }

                return null;
            }
        });

        return request.adapt().execute().body();
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

}
