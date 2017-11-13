package com.android.smartlink.ui.model;

import android.content.res.Resources;

import com.android.devin.core.ui.widget.recyclerview.DiffContentAnnotation;
import com.android.devin.core.ui.widget.recyclerview.DiffItemAnnotation;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Events.Event;

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
public class UIEvent extends UICompareObject<UIEvent>
{
    private Event mSource;

    private String mDate;

    private String mDateTime;

    private int[] mTextStatusColor;

    public UIEvent(Event event)
    {
        mSource = event;

        long time = mSource.getTime();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));

        calendar.setTimeInMillis(time);

        Date date = calendar.getTime();

        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.US); // default locale

        mDate = ymdFormat.format(date);

        SimpleDateFormat ymdHMFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.US); // default locale

        mDateTime = ymdHMFormat.format(date);

        Resources resources = AppManager.getInstance().getApplication().getResources();

        int normalColor = resources.getColor(R.color.module_status_none);

        int errorColor = resources.getColor(R.color.module_status_error);

        int warningColor = resources.getColor(R.color.module_status_warn);

        mTextStatusColor = new int[]{normalColor, errorColor, warningColor};
    }

    @DiffItemAnnotation
    public int getId()
    {
        return mSource.getId();
    }

    @DiffContentAnnotation
    public String getName()
    {
        return mSource.getDescription();
    }

    @DiffContentAnnotation
    public String getTime()
    {
        return mDateTime;
    }

    @DiffContentAnnotation
    public String getDate()
    {
        return mDate;
    }

    @DiffContentAnnotation
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
