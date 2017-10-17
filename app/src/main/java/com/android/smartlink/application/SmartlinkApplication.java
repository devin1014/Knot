package com.android.smartlink.application;

import android.app.Application;

import com.android.smartlink.application.manager.EquipmentManager;
import com.lzy.okgo.OkGo;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-15
 * Time: 14:49
 */
public class SmartlinkApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        OkGo.getInstance().init(this);

        EquipmentManager.initialize(this);
    }
}
