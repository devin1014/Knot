package com.android.smartlink.assist;

import android.app.Activity;

import com.android.devin.core.util.IOUtils;
import com.android.smartlink.bean.Weather;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-12
 * Time: 10:35
 */
public class WeatherProvider extends BaseRequestProvider<Weather>
{
    public WeatherProvider(Activity activity, RequestCallback<Weather> callback)
    {
        super(activity, callback);
    }

    @Override
    protected void getFromLocal(String url)
    {
        try
        {
            String resultString = IOUtils.parseStream(getActivity().getAssets().open("weather.json"));

            JSONObject jsonObject = new JSONObject(resultString);

            JSONObject weatherObj = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);

            Weather weather = new Gson().fromJson(weatherObj.toString(), Weather.class);

            notifyResponse(weather);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void getFromOkHttp(String url)
    {
        // call local first
        getFromLocal(url);

        OkGo.getInstance().cancelTag(this);

        OkGo.<Weather>get(url)

                .tag(this)

                .execute(new ResponseCallback());
    }

    @Override
    protected void getFromRemote(String url)
    {
        // ignore
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
}
