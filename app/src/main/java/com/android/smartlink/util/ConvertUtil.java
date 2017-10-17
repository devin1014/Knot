package com.android.smartlink.util;

import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UISetting;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

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

        for (Event item : list)
        {
            result.add(new UIEvent(item));
        }

        return result;
    }

    public static List<UISetting> convert(String[] list)
    {
        if (list == null || list.length == 0)
        {
            return null;
        }

        List<UISetting> result = new ArrayList<>();

        for (String item : list)
        {
            result.add(new UISetting(item));
        }

        return result;
    }
}
