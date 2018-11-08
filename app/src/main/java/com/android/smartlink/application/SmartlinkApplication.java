package com.android.smartlink.application;

import android.support.multidex.MultiDexApplication;

import com.android.devin.core.util.Debug;
import com.android.devin.core.util.LogUtil;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.commonsdk.UMConfigure;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * User: LIUWEI
 * Date: 2017-10-15
 * Time: 14:49
 */
public class SmartlinkApplication extends MultiDexApplication
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        final boolean debugMode = Utils.isDevDebugMode(this);

        Debug.init(debugMode); // 初始化Log,debug模式才输出

        LogUtil.set();

        // App
        {
            AppManager.initialize(this);
        }

        // OkGo初始化
        {
            OkGo.getInstance().init(this);

            OkGo.getInstance().setRetryCount(0);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);

            builder.readTimeout(15 * 1000, TimeUnit.MILLISECONDS);
            builder.writeTimeout(15 * 1000, TimeUnit.MILLISECONDS);
            builder.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);

            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);

            OkGo.getInstance().setOkHttpClient(builder.build());
        }

        // 友盟初始化
        {
            UMConfigure.setLogEnabled(debugMode);

            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);

            MobclickAgent.setDebugMode(debugMode);

            MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);

            MobclickAgent.openActivityDurationTrack(false);
        }

        LogUtil.test(this, "Application onCreate");
    }
}
