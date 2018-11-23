package com.android.smartlink.ui.model;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.model.BaseModule.Module.Status;

import java.util.List;

public class ModuleBarImp implements UIModuleBar
{
    private int status;

    private int textColor;

    private String title;

    public ModuleBarImp(List<UIMonitorModule> list)
    {
        String[] arrays = AppManager.getInstance().getStringArray(R.array.module_status_array);

        status = Status.STATUS_NORMAL;

        for (UIMonitorModule module : list)
        {
            int s = module.getModuleStatus();

            if (s == Status.STATUS_ERROR)
            {
                status = s;

                break;
            }
            else if (s == Status.STATUS_WARNING)
            {
                status = s;
            }
        }

        title = arrays[status];

        int[] colors = new int[]{R.color.module_status_good, R.color.module_status_error, R.color.module_status_warn};

        textColor = AppManager.getInstance().getApplication().getResources().getColor(colors[status]);
    }

    @Override
    public int getStatus()
    {
        return status;
    }

    @Override
    public int getTextColor()
    {
        return textColor;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public boolean isSelected()
    {
        return status != Status.STATUS_NORMAL;
    }
}
