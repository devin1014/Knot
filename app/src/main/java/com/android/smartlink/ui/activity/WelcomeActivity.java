package com.android.smartlink.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.util.Utils;
import com.umeng.analytics.MobclickAgent;

public class WelcomeActivity extends AppCompatActivity implements OnClickListener
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Utils.resetOrientation(this);

        setContentView(R.layout.activity_welcome);

        findViewById(R.id.sign_in).setOnClickListener(this);

        findViewById(R.id.demo_mode).setOnClickListener(this);
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.sign_in:

                AppManager.getInstance().setDemoMode(false);

                MobclickAgent.onEvent(this, "click_product_signin");

                break;

            case R.id.demo_mode:

                AppManager.getInstance().setDemoMode(true);

                MobclickAgent.onEvent(this, "click_test_signin");

                break;
        }

        finish();

        startActivity(new Intent(this, MainActivity.class));
    }
}
