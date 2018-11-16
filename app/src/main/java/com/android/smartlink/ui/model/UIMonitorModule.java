package com.android.smartlink.ui.model;

import com.android.smartlink.ui.model.BaseModule.Module;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:41
 */
public interface UIMonitorModule extends Module
{
    String getEnergy();

    int getModuleStatus();

    int getPowerLoad();

    int getColor();

    boolean hasAlarm();

    boolean isToggle();
}
