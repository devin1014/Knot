package com.android.smartlink.ui.model;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.bean.Modbus.EquipmentToggle;
import com.android.smartlink.bean.Modules.Toggle;
import com.android.smartlink.util.Helper.ToggleHelper;
import com.android.smartlink.util.ViewUtil;
import com.neulion.recyclerdiff.annotation.DiffContent;
import com.neulion.recyclerdiff.annotation.DiffItem;

import java.io.Serializable;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-31
 * Time: 20:58
 */
public class UIToggle implements Serializable, IModule
{
    private static final long serialVersionUID = -2506753032922419481L;

    private Toggle mToggle;

    public UIToggle(Toggle toggle)
    {
        mToggle = toggle;

        toggleOn = ToggleHelper.isToggleOn(toggle);
    }

    @DiffItem
    @Override
    public int getId()
    {
        return AppManager.getInstance().getToggle(mToggle.getChannel()).getId();
    }

    @Override
    public int getImageRes()
    {
        EquipmentToggle toggle = AppManager.getInstance().getToggle(mToggle.getChannel());

        return ViewUtil.getDrawable(AppManager.getInstance().getApplication(), toggle.getIcon());
    }

    @Override
    public int getWhiteImageRes()
    {
        return getImageRes();
    }

    @Override
    public String getName()
    {
        EquipmentToggle toggle = AppManager.getInstance().getToggle(mToggle.getChannel());

        return toggle.getName();
    }

    @Override
    public String getEnergy()
    {
        return null;
    }

    @DiffContent
    @Override
    public int getStatus()
    {
        return mToggle.getStatus();
    }

    @Override
    public int getPowerLoad()
    {
        return 0;
    }

    @Override
    public int getColor()
    {
        return 0;
    }

    @Override
    public boolean isNormal()
    {
        return true;
    }

    @Override
    public boolean isToggle()
    {
        return true;
    }

    private boolean toggleOn;

    public void setToggleOn(boolean on)
    {
        toggleOn = on;
    }

    @DiffContent
    public boolean isToggleOn()
    {
        return toggleOn;
    }
}
