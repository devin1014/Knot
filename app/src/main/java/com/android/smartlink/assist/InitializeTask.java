package com.android.smartlink.assist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.SystemClock;

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

        boolean isPhone = AppManager.getInstance().isPhone();

        Gson gson = new Gson();

        try
        {
            Equipments equipments = gson.fromJson(new InputStreamReader(mAssetManager.open(isPhone ? "equipment.json" : "equipment_tablet.json")), Equipments.class);

            AppManager.getInstance().setEquipments(equipments);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            return false;
        }

        try
        {
            //            Weather weather = AppManager.getInstance().getWeather();
            //
            //            if (weather == null)
            //            {
            //                String location = AppManager.getInstance().getLocation();
            //
            //                if (TextUtils.isEmpty(location))
            //                {
            //                    location = getLocation();
            //
            //                    AppManager.getInstance().setLocation(location);
            //                }
            //
            //                weather = getWeather(location);
            //
            //                if (weather == null)
            //                {
            //                    String resultString = IOUtils.parseStream(mAssetManager.open("weather.json"));
            //
            //                    JSONObject jsonObject = new JSONObject(resultString);
            //
            //                    JSONObject weatherObj = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);
            //
            //                    weather = new Gson().fromJson(weatherObj.toString(), Weather.class);
            //                }
            //
            //                AppManager.getInstance().setWeather(weather);
            //            }

            if (SystemClock.uptimeMillis() - timeStamp < 1500)
            {
                Thread.sleep(1000);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    private String getLocation() throws Exception
    {
        GetRequest<WeatherLocation> request = OkGo.get(HttpUrl.getAccuWeatherUrl(AppManager.getInstance().getApplication()));

        request.converter(new Converter<WeatherLocation>()
        {
            @Override
            public WeatherLocation convertResponse(okhttp3.Response response) throws Throwable
            {
                //noinspection ConstantConditions
                return new Gson().fromJson(response.body().string(), WeatherLocation.class);
            }
        });

        WeatherLocation location = request.adapt().execute().body();

        return location != null ? location.getLocation() : Constants.DEFAULT_LOCATION;
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
