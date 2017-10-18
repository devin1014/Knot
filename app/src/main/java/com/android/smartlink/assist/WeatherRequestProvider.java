package com.android.smartlink.assist;

import android.os.AsyncTask;

import com.android.smartlink.bean.Weather;
import com.android.smartlink.util.FileUtil;
import com.lzy.okgo.OkGo;

import java.util.Calendar;
import java.util.TimeZone;

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

    private String mUrl;

    @Override
    public void request(String url)
    {
        mUrl = url;

        if (FileUtil.hasWeatherCache())
        {
            new ReadFileTask().execute();
        }
        else
        {
            OkGo.getInstance().cancelTag(this);

            OkGo.<Weather>get(url)

                    .tag(this)

                    .execute(new ResponseCallback()
                    {
                        @Override
                        Class<Weather> getConvertObjectClass()
                        {
                            return Weather.class;
                        }
                    });
        }
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
        super.notifyResponse(weather);

        new WriteFileTask(weather).execute();
    }

    private String getTodayKey()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        return String.valueOf(calendar.get(Calendar.YEAR)) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DAY_OF_MONTH);
    }

    private class WriteFileTask extends AsyncTask<Void, Void, Boolean>
    {
        private Weather mWeather;

        WriteFileTask(Weather weather)
        {
            mWeather = weather;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            return FileUtil.writeWeatherFile(mWeather);
        }
    }

    private class ReadFileTask extends AsyncTask<Void, Void, Weather>
    {
        @Override
        protected Weather doInBackground(Void... params)
        {
            return FileUtil.readWeatherFile();
        }

        @Override
        protected void onPostExecute(Weather weather)
        {
            if (weather != null)
            {
                notifyResponse(weather);

                if (!weather.getDate().equals(getTodayKey()))
                {
                    if (!isDestroy())
                    {
                        request(mUrl);
                    }
                }
            }
        }
    }
}
