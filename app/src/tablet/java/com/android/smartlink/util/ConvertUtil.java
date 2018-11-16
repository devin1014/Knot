package com.android.smartlink.util;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.AllFilter;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.bean.ModulesData.ToggleModuleData;
import com.android.smartlink.ui.model.BaseModule;
import com.android.smartlink.ui.model.BaseModule.Module.GroupType;
import com.android.smartlink.ui.model.BaseModule.Module.ImageType;
import com.android.smartlink.ui.model.BaseModule.ModuleParser;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.model.ToggleModuleImp;
import com.android.smartlink.ui.model.UIDeviceImp;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.android.smartlink.ui.model.UISetting;
import com.android.smartlink.ui.model.UIToggleModule;

import java.util.ArrayList;
import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 16:04
 */
public class ConvertUtil
{
    // --------------------------------------------------------------------------------------------------------------
    // - Monitor
    // --------------------------------------------------------------------------------------------------------------
    public static List<UIMonitorModule> convertModules(List<MonitorModuleData> list)
    {
        return convertModules(list, ImageType.DRAWABLE_NORMAL);
    }

    public static List<UIMonitorModule> convertModules(List<MonitorModuleData> list, @ImageType int imageType)
    {
        return convertModules(list, 0, list.size(), imageType);
    }

    public static List<UIMonitorModule> convertModules(List<MonitorModuleData> list, int from, int to, @ImageType int imageType)
    {
        return convertModules(list, from, to, imageType, GroupType.GROUP_ALL);
    }

    public static List<UIMonitorModule> convertModules(List<MonitorModuleData> list, int from, int to, @ImageType int imageType, @GroupType int group)
    {
        List<UIMonitorModule> moduleList = new ArrayList<>();

        for (int i = from; list != null && i < to; i++)
        {
            boolean add = true;

            if (group != GroupType.GROUP_ALL)
            {
                int generateId = ModuleParser.generateId(list.get(i));

                if (group != AppManager.getInstance().getModuleGroup(generateId))
                {
                    add = false;
                }
            }

            if (add)
            {
                moduleList.add(new MonitorModuleImp(list.get(i), imageType));
            }
        }

        return moduleList;
    }

    public static UIMonitorModule convertModule(MonitorModuleData moduleData, int imageType)
    {
        return new MonitorModuleImp(moduleData, imageType);
    }

    // --------------------------------------------------------------------------------------------------------------
    // - Toggle
    // --------------------------------------------------------------------------------------------------------------
    public static List<UIToggleModule> convertToggle(List<ToggleModuleData> list)
    {
        List<UIToggleModule> moduleList = new ArrayList<>();

        if (list != null)
        {
            for (ToggleModuleData data : list)
            {
                moduleList.add(new ToggleModuleImp(data));
            }
        }

        return moduleList;
    }

    // --------------------------------------------------------------------------------------------------------------
    // - Event
    // --------------------------------------------------------------------------------------------------------------
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

    public static List<UIFilter> convertFilters(List<BaseModule> list, int[] ids, boolean addOthers)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }

        List<UIFilter> result = new ArrayList<>();

        for (BaseModule item : list)
        {
            if (ids == null || ids.length == 0)
            {
                result.add(new UIFilter(item));
            }
            else
            {
                for (int id : ids)
                {
                    UIFilter filter = new UIFilter(item);

                    if (filter.getId() == id)
                    {
                        result.add(filter);

                        break;
                    }
                }
            }
        }

        if (addOthers)
        {
            result.add(new UIFilter(new AllFilter()));
        }

        return result;
    }

    // --------------------------------------------------------------------------------------------------------------
    // - Settings
    // --------------------------------------------------------------------------------------------------------------
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

    public static List<UIDeviceImp> convertEquipment(List<BaseModule> list)
    {
        if (list == null || list.size() == 0)
        {
            return null;
        }

        List<UIDeviceImp> result = new ArrayList<>();

        for (BaseModule item : list)
        {
            result.add(new UIDeviceImp(item));
        }

        return result;
    }
}
