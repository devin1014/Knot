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
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

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

            final PushAgent mPushAgent = PushAgent.getInstance(this);
            //注册推送服务，每次调用register方法都会回调该接口
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    mPushAgent.register(new IUmengRegisterCallback()
                    {
                        @Override
                        public void onSuccess(String deviceToken)
                        {
                            //注册成功会返回device token
                            LogUtil.log(SmartlinkApplication.this, "IUmengRegisterCallback deviceToken:" + deviceToken);
                        }

                        @Override
                        public void onFailure(String s, String s1)
                        {
                            LogUtil.log(SmartlinkApplication.this, "IUmengRegisterCallback onFailure:" + s + "," + s1);
                        }
                    });
                }
            }).start();
        }

        // App
        {
            LogUtil.init(this); // 初始化Log,debug模式才输出

            AppManager.initialize(this);
        }

        TestUtil.test(this, "onCreate");
    }
}
