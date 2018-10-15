package com.android.smartlink.assist;

import android.app.Activity;

import com.android.smartlink.bean.WeatherLocation;
import com.lzy.okgo.OkGo;

/**
 * User: LIUWEI
 * Date: 2017-12-25
 * Time: 11:52
 */
public class LocationProvider extends BaseRequestProvider<WeatherLocation>
{
    LocationProvider(Activity activity, RequestCallback<WeatherLocation> callback)
    {
        super(activity, callback);
    }

    @Override
    protected void getFromLocal(String url)
    {
        notifyResponse(new WeatherLocation());
    }

    @Override
    protected void getFromOkHttp(String url)
    {
        OkGo.getInstance().cancelTag(this);

        OkGo.<WeatherLocation>get(url)

                .tag(this)

                .execute(new ResponseCallback());
    }

    @Override
    protected void getFromRemote(String url)
    {
    }

    @Override
    Class<WeatherLocation> getConvertObjectClass()
    {
        return WeatherLocation.class;
    }
}
