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

    private String mEndTime;

    private String mDate;

    private int mWeek;

    private int mDay;

    public UIEvent(Event event)
    {
        mSource = event;

        if (mSource.getStartTimeStamp() != 0)
        {
            parseDate(mSource.getStartTimeStamp(), mSource.getEndTimeStamp());
        }
        else if (mSource.getStartTime() != null)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd't'HH:mm:ss", Locale.US);

            try
            {
                Date startTime = format.parse(mSource.getStartTime());

                Date endTime = format.parse(mSource.getEndTime());

                parseDate(startTime.getTime(), endTime.getTime());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void parseDate(long startTime, long endTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd", Locale.getDefault()); // default locale

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTimeInMillis(startTime);

        mDay = calendar.get(Calendar.DAY_OF_YEAR);

        mStartTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        mWeek = calendar.get(Calendar.DAY_OF_WEEK);

        mDate = format.format(calendar.getTime());

        calendar.setTimeInMillis(endTime);

        mEndTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);


    }

    public String getName()
    {
        return mSource.getDescription();
    }

    public String getTime()
    {
        return getWeek() + " " + mStartTime + " and " + mEndTime;
    }

    public int getDay()
    {
        return mDay;
    }

    public int getWeek()
    {
        return mWeek;
    }

    public String getDate()
    {
        return mDate;
    }
}
