package com.android.smartlink.assist;

import android.app.Activity;

import com.android.devin.core.util.IOUtils;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.bean.Weather.WeatherBasic;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * User: LIUWEI
 * Date: 2017-11-12
 * Time: 10:35
 */
public abstract class WeatherProvider extends BaseRequestProvider<Weather>
{
    public static WeatherProvider newInstance(Activity activity, RequestCallback<Weather> callback)
    {
        return new LocalProvider(activity, callback);
    }

    WeatherProvider(Activity activity, RequestCallback<Weather> callback)
    {
        super(activity, callback);
    }

    @Override
    protected void notifyResponse(Weather weather)
    {
        super.notifyResponse(weather);

        AppManager.getInstance().setWeather(weather);
    }

    @Override
    protected void notifyResponse(Throwable throwable)
    {
        super.notifyResponse(throwable);
    }

    @Override
    Class<Weather> getConvertObjectClass()
    {
        return Weather.class;
    }

    @Override
    protected Weather convertResponse(Response response) throws Throwable
    {
        @SuppressWarnings("ConstantConditions")
        JSONObject jsonObject = new JSONObject(response.body().string());

        JSONObject weather = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);

        return new Gson().fromJson(weather.toString(), Weather.class);
    }

    static class LocalProvider extends WeatherProvider
    {
        LocalProvider(Activity activity, RequestCallback<Weather> callback)
        {
            super(activity, callback);
        }

        @Override
        public void request(String url)
        {
            try
            {
                String resultString = IOUtils.parseStream(getActivity().getAssets().open("data/weather.json"));

                JSONObject jsonObject = new JSONObject(resultString);

                JSONObject weatherObj = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);

                Weather weather = new Gson().fromJson(weatherObj.toString(), Weather.class);

                notifyResponse(weather);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    static class HttpProvider extends WeatherProvider
    {
        HttpProvider(Activity activity, RequestCallback<Weather> callback)
        {
            super(activity, callback);
        }

        @Override
        public void request(String url)
        {
            if (AppManager.getInstance().getWeather() != null)
            {
                if (!needUpdate(AppManager.getInstance().getWeather()))
                {
                    notifyResponse(AppManager.getInstance().getWeather());

                    return;
                }
            }

            OkGo.getInstance().cancelTag(this);

            OkGo.<Weather>get(url)

                    .tag(this)

                    .execute(new ResponseCallback());
        }

        private boolean needUpdate(Weather weather)
        {
            WeatherBasic weatherBasic = weather.getBasic();

            long duration = System.currentTimeMillis() - weatherBasic.getUpdateTime().getTime();

            return duration < 0 || duration > 6 * 60 * 60 * 1000;
        }
    }
}
