package com.android.smartlink.assist;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Weather;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 14:25
 */
public class WeatherRequestProvider extends BaseRequestProvider<Weather>
{
    public WeatherRequestProvider(RequestCallback<Weather> callback)
    {
        super(callback);
    }

    @Override
    Class<Weather> getConvertObjectClass()
    {
        return Weather.class;
    }

    private String mUrl;

    @Override
    public void request(String url)
    {
        mUrl = url;

        //// TODO: 2017/10/25
        if (AppManager.getInstance().getWeather() != null)
        {
            super.notifyResponse(AppManager.getInstance().getWeather());
        }
        else
        {
            requestInternet();
        }
    }

    private void requestInternet()
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<Weather>get(mUrl)

                .tag(this)

                .execute(new ResponseCallback());
    }

    @Override
    public void destroy()
    {
        OkGo.getInstance().cancelTag(this);

        super.destroy();
    }

    @Override
    protected void notifyResponse(Weather weather)
    {
        AppManager.getInstance().setWeather(weather);

        super.notifyResponse(weather);
    }

    @Override
    protected Weather convertResponse(Response response) throws Throwable
    {
        if (response.code() == 200 && response.body() != null)
        {
            JSONObject jsonObject = new JSONObject(response.body().string());

            JSONObject weather = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);

            return new Gson().fromJson(weather.toString(), getConvertObjectClass());
        }

        return null;
    }
}
