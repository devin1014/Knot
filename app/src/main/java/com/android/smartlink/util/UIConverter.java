package com.android.smartlink.util;

import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.bean.Modules.Toggle;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UIToggle;

import java.util.ArrayList;
import java.util.List;

/**
 * User: liuwei
 * Date: 2018-05-18
 * Time: 15:23
 */
public class UIConverter
{
    public static List<UIModule> convertModules(List<Module> list)
    {
        List<UIModule> moduleList = new ArrayList<>();

        for (int i = 0; list != null && i < list.size(); i++)
        {
            UIModule m = new UIModule(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }

    public static List<UIToggle> convertToggle(List<Toggle> list)
    {
        List<UIToggle> moduleList = new ArrayList<>();

        for (int i = 0; list != null && i < list.size(); i++)
        {
            UIToggle m = new UIToggle(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }

    public static List<UIToggle> convertToggle(List<Toggle> list, int startIndex, int count)
    {
        List<UIToggle> moduleList = new ArrayList<>();

        for (int i = startIndex; list != null && i < startIndex + count && i < list.size(); i++)
        {
            UIToggle m = new UIToggle(list.get(i));

            moduleList.add(m);
        }

        return moduleList;
    }
}
