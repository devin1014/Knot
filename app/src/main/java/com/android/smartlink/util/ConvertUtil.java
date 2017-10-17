package com.android.smartlink.util;

import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.ui.model.UIEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:04
 */
public class ConvertUtil
{
    public static List<UIEvent> convert(List<Event> list)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }

        List<UIEvent> result = new ArrayList<>();

        for (Event event : list)
        {
            result.add(new UIEvent(event));
        }

        return result;
    }
}
