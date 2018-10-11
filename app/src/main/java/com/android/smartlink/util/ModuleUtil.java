package com.android.smartlink.util;

import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Modules.Module;

/**
 * User: LIUWEI
 * Date: 2018-05-25
 * Time: 16:41
 */
public class ModuleUtil
{
    public static String getModuleString(Modules modules)
    {
        StringBuilder builder = new StringBuilder();

        for (Module m : modules.getModules())
        {
            builder.append("id=").append(m.getId()).append(",status=").append(m.getStatus()).append("\n");
        }

        return builder.toString();
    }
}
