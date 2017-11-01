package com.android.smartlink.ui.model;

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
public class UIEvent
{
    private Event mSource;

    private String mStartTime;

    private String mDate;

    private int mWeek;

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
    }

    private void parseDate(long startTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.US); // default locale

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTimeInMillis(startTime);

        mStartTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        mWeek = calendar.get(Calendar.DAY_OF_WEEK);

        mDate = format.format(calendar.getTime());
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
        return getDate() + " " + mStartTime;
    }

    public int getWeek()
    {
        return mWeek;
    }

    public String getDate()
    {
        return mDate;
    }

    public int getStatus()
    {
        return mSource.getStatus();
    }
}
