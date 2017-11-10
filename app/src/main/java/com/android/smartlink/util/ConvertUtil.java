package com.android.smartlink.util;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Equipments.Equipment;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.ui.model.UIEquipment;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UISetting;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:04
 */
public class ConvertUtil
{
    public static List<UIEvent> convertEvents(List<Event> list)
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

    public static List<UISetting> convertSettings(String[] list, String[] res)
    {
        if (list == null || list.length == 0 || res == null || res.length == 0)
        {
            return null;
        }

        if (list.length != res.length)
        {
            return null;
        }

        List<UISetting> result = new ArrayList<>();

        for (int i = 0; i < list.length; i++)
        {
            result.add(new UISetting(list[i], res[i]));
        }

        return result;
    }

    public static List<UIEquipment> convertEquipment(List<Equipment> list)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }

        List<UIEquipment> result = new ArrayList<>();

        for (Equipment item : list)
        {
            result.add(new UIEquipment(item));
        }

        return result;
    }

    public static UIModule[] convertModule(List<Module> list, int start, int end)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }

        List<UIModule> result = new ArrayList<>();

        for (int i = start; i < list.size() && i < end; i++)
        {
            result.add(new UIModule(list.get(i)));
        }

        return result.toArray(new UIModule[result.size()]);
    }

    public static List<UIFilter> convertFilters(List<Equipment> list, int[] ids, boolean addOthers)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }

        List<UIFilter> result = new ArrayList<>();

        for (Equipment item : list)
        {
            if (ids == null || ids.length == 0)
            {
                result.add(new UIFilter(item.getId(), item.getName()));
            }
            else
            {
                for (int id : ids)
                {
                    if (item.getId() == id)
                    {
                        result.add(new UIFilter(item.getId(), item.getName()));

                        break;
                    }
                }
            }
        }

        if (addOthers)
        {
            result.add(new UIFilter(Constants.ID_ALL, AppManager.getInstance().getString(R.string.events_filter_other)));
        }

        return result;
    }

    public static String convertStatus(int status)
    {
        int mainStatus = status / 100;

        int freezerStatus = (status / 10) % 10;

        int ovenStatus = status % 10;

        return mainStatus + "_" + freezerStatus + "_" + ovenStatus;
    }
}
