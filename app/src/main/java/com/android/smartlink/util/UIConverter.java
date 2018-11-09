package com.android.smartlink.util;

import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.bean.Modules.Toggle;
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
    public static List<MonitorModuleImp> convertModules(List<Module> list)
    {
        List<MonitorModuleImp> moduleList = new ArrayList<>();

        for (int i = 0; list != null && i < list.size(); i++)
        {
            MonitorModuleImp m = new MonitorModuleImp(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }

    public static List<ToggleModuleImp> convertToggle(List<Toggle> list)
    {
        List<ToggleModuleImp> moduleList = new ArrayList<>();

        for (int i = 0; list != null && i < list.size(); i++)
        {
            ToggleModuleImp m = new ToggleModuleImp(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }

    public static List<ToggleModuleImp> convertToggle(List<Toggle> list, int startIndex, int count)
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
