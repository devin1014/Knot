package com.android.smartlink.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.InitializeTask;
import com.android.smartlink.assist.InitializeTask.InitializeTaskCallback;
import com.android.smartlink.util.AlertUtil;
import com.android.smartlink.util.Utils;
import com.umeng.analytics.MobclickAgent;

/**
 * User: LIUWEI
 * Date: 2017-10-15
 * Time: 14:23
 */
public class SplashActivity extends AppCompatActivity implements InitializeTaskCallback
{
    private InitializeTask mInitializeTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Utils.resetOrientation(this);

        setContentView(R.layout.activity_splash);

        (mInitializeTask = new InitializeTask(this, this)).execute();
    }

    @Override
    protected void onDestroy()
    {
        if (mInitializeTask != null)
        {
            mInitializeTask.destroy();
        }

        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        MobclickAgent.onResume(this);

        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    protected void onPause()
    {
        MobclickAgent.onPause(this);

        MobclickAgent.onPageEnd(getClass().getSimpleName());

        super.onPause();
    }

    @Override
    public void onInitialized(boolean success)
    {
        if (success)
        {
            finish();

            startActivity(new Intent(this, AppManager.getInstance().isPhone() ? WelcomeActivity.class : MainActivity.class));
        }
        else
        {
            AlertUtil.showLaunchFailedAlert(this);
        }
    }
}
