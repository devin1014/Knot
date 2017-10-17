package com.android.smartlink.ui.model;

import com.android.smartlink.bean.Events.Event;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 15:51
 */
public class UIEvent
{
    private Event mSource;

    public UIEvent(Event event)
    {
        mSource = event;
    }

    public String getName()
    {
        return mSource.getDescription();
    }

    public String getTime()
    {
        return mSource.getStartTime() + " - " + mSource.getEndTime();
    }
}
