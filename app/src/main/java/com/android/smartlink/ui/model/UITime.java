package com.android.smartlink.ui.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-06
 * Time: 17:01
 */
public class UITime
{
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm", Locale.US);

    public UITime()
    {
    }

    public String getTime()
    {
        return FORMAT.format(Calendar.getInstance().getTime());
    }

    public String getAMOrPM()
    {
        return Calendar.getInstance().get(Calendar.AM_PM) == 0 ? "AM" : "PM";
    }
}
