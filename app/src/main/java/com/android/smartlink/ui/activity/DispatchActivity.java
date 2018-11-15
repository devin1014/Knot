package com.android.smartlink.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.util.Utils;

/**
 * User: LIUWEI
 * Date: 2017-10-28
 * Time: 17:27
 */
public class DispatchActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Utils.resetOrientation(this);

        finish();

        if (AppManager.getInstance().isInitialized() && AppManager.getInstance().getWeather() != null)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        else
        {
            startActivity(new Intent(this, SplashActivity.class));
        }
    }
}
