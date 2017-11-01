package com.android.smartlink.ui.model;

import android.content.res.Resources;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Events.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 15:51
 */
public class UIEvent implements UIAlarm
{
    private Event mSource;

    private String mDate;

    private String mDateTime;

    private int[] mTextStatusColor;

    public UIEvent(Event event)
    {
        mSource = event;

        if (mSource.getStartTimeStamp() != 0)
        {
            parseDate(mSource.getStartTimeStamp());
        }
        else if (mSource.getStartTime() != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd't'HH:mm:ss", Locale.US);

            try
            {
                Date startTime = format.parse(mSource.getStartTime());

                parseDate(startTime.getTime());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        Resources resources = AppManager.getInstance().getApplication().getResources();

        mTextStatusColor = new int[]{resources.getColor(R.color.module_status_none), resources.getColor(R.color.module_status_warn), resources.getColor(R.color.module_status_error)};

    }

    private void parseDate(long startTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.US); // default locale

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

        calendar.setTimeInMillis(startTime);

        mDate = format.format(calendar.getTime());

        format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.US); // default locale

        calendar.setTimeInMillis(startTime);

        mDateTime = format.format(calendar.getTime());
    }

    public int getId()
    {
        return mSource.getId();
    }

    public String getName()
    {
        return mSource.getDescription();
    }

    public String getTime()
    {
        return mDateTime;
    }

    public String getDate()
    {
        return mDate;
    }

    public int getStatus()
    {
        return mSource.getStatus();
    }

    public int getTextColor()
    {
        if (mSource.getStatus() > mTextStatusColor.length || mSource.getStatus() < 0)
        {
            return mTextStatusColor[0];
        }

        return mTextStatusColor[mSource.getStatus()];
    }
}
