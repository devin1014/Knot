package com.android.smartlink.ui.model;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.bean.Weather.WeatherCond;
import com.android.smartlink.bean.Weather.WeatherDailyForecast;

import java.util.Calendar;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-25
 * Time: 15:24
 */
public class UIWeather
{
    private Weather mWeather;

    private WeatherDailyForecast mTomorrowForecast;

    private WeatherDailyForecast mAfterTomorrowForecast;

    public UIWeather(Weather weather)
    {
        mWeather = weather;

        mTomorrowForecast = weather.getDaily_forecast().get(1);

        mAfterTomorrowForecast = weather.getDaily_forecast().get(2);
    }

    public String getAqi()
    {
        if (mWeather.getAqi() == null)
        {
            return AppManager.getInstance().getString(R.string.weather_aqi_none);
        }

        return mWeather.getAqi().getAqi() + "/" + mWeather.getAqi().getQlty();
    }

    public int getAqiValue()
    {
        if (mWeather.getAqi() == null)
        {
            return 0;
        }

        return mWeather.getAqi().getAqi();
    }

    public String getTmp()
    {
        return String.format(AppManager.getInstance().getString(R.string.format_weather_tmp), mWeather.getNow().getTmp());
    }

    public String getTodayMinMax()
    {
        WeatherDailyForecast dailyForecast = mWeather.getDaily_forecast().get(0);

        return String.format(AppManager.getInstance().getString(R.string.format_weather_today), dailyForecast.getMin(), dailyForecast.getMax());
    }

    public String getTomorrowMinMax()
    {
        return String.format(AppManager.getInstance().getString(R.string.format_weather_min_max), mTomorrowForecast.getMin(), mTomorrowForecast.getMax());

    }

    public String getAfterTomorrowMinMax()
    {
        return String.format(AppManager.getInstance().getString(R.string.format_weather_min_max), mAfterTomorrowForecast.getMin(), mAfterTomorrowForecast.getMax());
    }

    public String getImage()
    {
        return getImage(mWeather.getNow().getCond());
    }

    public String getTomorrowImage()
    {
        return getImage(mWeather.getDaily_forecast().get(1).getCond());
    }

    public String getDayAfterTomorrowImage()
    {
        return getImage(mWeather.getDaily_forecast().get(2).getCond());
    }

    private String getImage(WeatherCond cond)
    {
        int code = cond.getCode();

        if (code == 0)
        {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

            if (hour >= 18)
            {
                code = cond.getCode_n();
            }
            else
            {
                code = cond.getCode_d();
            }
        }

        return "weather_" + code;
    }
}
