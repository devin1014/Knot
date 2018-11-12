package com.android.smartlink.util;

import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.bean.ModulesData.ToggleModuleData;
import com.android.smartlink.ui.model.IModule;
import com.android.smartlink.ui.model.IModule.ImageType;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.model.ToggleModuleImp;

import java.util.ArrayList;
import java.util.List;

/**
 * User: liuwei
 * Date: 2018-05-18
 * Time: 15:23
 */
public class UIConverter
{
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

    public static IModule convertModule(List<MonitorModuleData> list, int index, int imageType)
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
}
