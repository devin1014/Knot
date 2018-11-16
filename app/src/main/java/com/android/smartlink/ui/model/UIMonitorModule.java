package com.android.smartlink.ui.model;

import android.support.annotation.IntDef;

import com.android.smartlink.ui.model.BaseModule.Module;

import static com.android.smartlink.ui.model.UIMonitorModule.GroupType.GROUP_1;
import static com.android.smartlink.ui.model.UIMonitorModule.GroupType.GROUP_2;
import static com.android.smartlink.ui.model.UIMonitorModule.GroupType.GROUP_ALL;
import static com.android.smartlink.ui.model.UIMonitorModule.ImageType.DRAWABLE_LARGE;
import static com.android.smartlink.ui.model.UIMonitorModule.ImageType.DRAWABLE_LARGE_LIGHT;
import static com.android.smartlink.ui.model.UIMonitorModule.ImageType.DRAWABLE_NORMAL;
import static com.android.smartlink.ui.model.UIMonitorModule.ImageType.DRAWABLE_NORMAL_LIGHT;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:41
 */
public interface UIMonitorModule extends Module
{
    @IntDef({DRAWABLE_NORMAL, DRAWABLE_NORMAL_LIGHT, DRAWABLE_LARGE, DRAWABLE_LARGE_LIGHT})
    @interface ImageType
    {
        int DRAWABLE_NORMAL = 0;
        int DRAWABLE_NORMAL_LIGHT = 1;
        int DRAWABLE_LARGE = 2;
        int DRAWABLE_LARGE_LIGHT = 3;
    }

    @IntDef({GROUP_ALL, GROUP_1, GROUP_2})
    @interface GroupType
    {
        int GROUP_ALL = 0;
        int GROUP_1 = 1;
        int GROUP_2 = 2;
    }

    String getEnergy();

    int getModuleStatus();

    int getPowerLoad();

    int getColor();

    boolean hasAlarm();

    boolean isToggle();
}
