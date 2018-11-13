package com.android.smartlink.util;

import com.android.smartlink.bean.AllFilter;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.bean.ModulesData.ToggleModuleData;
import com.android.smartlink.ui.model.BaseModule;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.model.ToggleModuleImp;
import com.android.smartlink.ui.model.UIDeviceImp;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.android.smartlink.ui.model.UIMonitorModule.ImageType;
import com.android.smartlink.ui.model.UISetting;

import java.util.ArrayList;
import java.util.List;

/**
 * User: LIUWEI
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

    //    public static List<MonitorModuleImp> convertModule(List<MonitorModuleData> list, int start, int end)
    //    {
    //        List<MonitorModuleImp> result = new ArrayList<>();
    //
    //        if (list != null)
    //        {
    //            for (int i = start; i < list.size() && i < end; i++)
    //            {
    //                result.add(new MonitorModuleImp(list.get(i)));
    //            }
    //        }
    //
    //        return result;
    //    }

    public static List<UIMonitorModule> convertModule(ModulesData modules)
    {
        List<UIMonitorModule> result = new ArrayList<>();

        List<MonitorModuleData> list = modules.getMonitorModules();

        if (list != null)
        {
            for (MonitorModuleData m : list)
            {
                result.add(new MonitorModuleImp(m));
            }
        }

        List<ToggleModuleData> toggles = modules.getToggleModules();

        if (toggles != null)
        {
            for (ToggleModuleData t : toggles)
            {
                //FIXME,should add toggle in phone
                //result.add(new ToggleModuleImp(t));
            }
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

    public static List<MonitorModuleImp> convertModules(List<MonitorModuleData> list, @ImageType int imageType)
    {
        return convertModules(list, 0, list.size(), imageType);
    }

    public static List<MonitorModuleImp> convertModules(List<MonitorModuleData> list, int from, int to, int imageType)
    {
        List<MonitorModuleImp> moduleList = new ArrayList<>();

        for (int i = from; list != null && i < to; i++)
        {
            MonitorModuleImp m = new MonitorModuleImp(list.get(i), imageType);

            moduleList.add(m);
        }

        return moduleList;
    }

    public static UIMonitorModule convertModule(List<MonitorModuleData> list, int index, int imageType)
    {
        return new MonitorModuleImp(list.get(index), imageType);
    }

    public static List<ToggleModuleImp> convertToggle(List<ToggleModuleData> list)
    {
        List<ToggleModuleImp> moduleList = new ArrayList<>();

        for (int i = 0; list != null && i < list.size(); i++)
        {
            ToggleModuleImp m = new ToggleModuleImp(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }

    public static List<ToggleModuleImp> convertToggle(List<ToggleModuleData> list, int startIndex, int count)
    {
        List<ToggleModuleImp> moduleList = new ArrayList<>();

        for (int i = startIndex; list != null && i < startIndex + count && i < list.size(); i++)
        {
            ToggleModuleImp m = new ToggleModuleImp(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }

    public static String convertStatus(int status)
    {
        if (status == -1)
        {
            return "all";
        }

        int mainStatus = status / 100;

        int freezerStatus = (status / 10) % 10;

        int ovenStatus = status % 10;

        return mainStatus + "_" + freezerStatus + "_" + ovenStatus;
    }
}
