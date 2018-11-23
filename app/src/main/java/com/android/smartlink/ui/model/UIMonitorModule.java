package com.android.smartlink.ui.model;

import com.android.smartlink.ui.model.BaseModule.Module;
import com.neulion.android.diffrecycler.diff.DataDiffCompare;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:41
 */
public interface UIMonitorModule extends Module, DataDiffCompare<UIMonitorModule>
{
    String getEnergy();

    int getModuleStatus();

    int getPowerLoad();

    int getColor();

    boolean hasAlarm();

    boolean isToggle();
}
