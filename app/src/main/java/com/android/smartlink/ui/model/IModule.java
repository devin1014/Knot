package com.android.smartlink.ui.model;

/**
 * User: LIUWEI
 * Date: 2017-12-31
 * Time: 20:41
 */
public interface IModule
{
    int getId();

    int getImageRes();

    int getWhiteImageRes();

    String getName();

    String getEnergy();

    int getStatus();

    int getPowerLoad();

    int getColor();

    boolean isNormal();

    boolean isToggle();
}
