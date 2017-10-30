package com.android.smartlink.application;

import android.app.Application;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.util.LogUtil;
import com.android.smartlink.util.TestUtil;
import com.android.smartlink.util.Utils;
import com.lzy.okgo.OkGo;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.commonsdk.UMConfigure;

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

        TestUtil.set();

        final boolean debugMode = Utils.isDevDebugMode(this);

        // OkGo初始化
        {
            OkGo.getInstance().init(this);
        }

        // 友盟初始化
        {
            UMConfigure.setLogEnabled(debugMode);

            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

            MobclickAgent.setDebugMode(debugMode);

            MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
        }

        // App
        {
            LogUtil.init(this); // 初始化Log,debug模式才输出

            AppManager.initialize(this);
        }

        TestUtil.test(this, "onCreate");
    }
}
