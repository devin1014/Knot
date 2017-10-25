package com.android.smartlink.ui.model;

import android.os.Parcel;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.util.Utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-25
 * Time: 15:24
 */
public class UIWeather
{
    private Weather mWeather;

    private String mTomorrow;

    private String mDayAfterTomorrow;

    public UIWeather(Weather weather)
    {
        mWeather = weather;

        Calendar mCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

        mCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        String[] weeks = AppManager.getInstance().getStringArray(R.array.week_array);

        mCalendar.add(Calendar.DAY_OF_WEEK, 1);

        mTomorrow = weeks[mCalendar.get(Calendar.DAY_OF_WEEK)];

        mCalendar.add(Calendar.DAY_OF_WEEK, 1);

        mDayAfterTomorrow = weeks[mCalendar.get(Calendar.DAY_OF_WEEK)];
    }

    public String getImage()
    {
        return "weather_" + mWeather.getNow().getCond().getCode();
    }

    public String getAqi()
    {
        return AppManager.getInstance().getString(R.string.weather_aqi) + mWeather.getAqi().getQlty();
    }

    public CharSequence getTmp()
    {
        return getTmp(mWeather.getNow().getTmp(), 1.2f, 0.5f);
    }

    public String getMaxTmp()
    {
        return AppManager.getInstance().getString(R.string.weather_max_tmp) + mWeather.getDaily_forecast().get(0).getMax();
    }

    public String getMinTemp()
    {
        return AppManager.getInstance().getString(R.string.weather_min_tmp) + mWeather.getDaily_forecast().get(0).getMin();
    }

    public String getTomorrowName()
    {
        return mTomorrow;
    }

    public CharSequence getTomorrowTmp()
    {
        return getTmp(mWeather.getDaily_forecast().get(1).getMax(), 1f, 0.8f);
    }

    public String getTomorrowImage()
    {
        return "weather_" + mWeather.getDaily_forecast().get(1).getCond().getCode_d();
    }

    public String getDayAfterTomorrowName()
    {
        return mDayAfterTomorrow;
    }

    public CharSequence getDayAfterTomorrowTmp()
    {
        return getTmp(mWeather.getDaily_forecast().get(2).getMax(), 1f, 0.8f);
    }

    public String getDayAfterTomorrowImage()
    {
        return "weather_" + mWeather.getDaily_forecast().get(2).getCond().getCode_d();
    }

    private CharSequence getTmp(int tmp, float scale, float scale2)
    {
        String string = tmp + "o";

        Parcel parcel = Parcel.obtain();

        parcel.writeString("o");

        SpannableStringBuilder builder = new SpannableStringBuilder(string);

        Utils.setSpannable(builder, string.indexOf("o"), string.indexOf("o") + 1, new SuperscriptSpan(parcel), new RelativeSizeSpan(scale2));

        Utils.setSpannable(builder, 0, string.indexOf("o"), new RelativeSizeSpan(scale));

        parcel.recycle();

        return builder;
    }
}
