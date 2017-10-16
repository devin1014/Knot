package com.android.smartlink.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.smartlink.R;
import com.android.smartlink.assist.InitializeTask;
import com.android.smartlink.assist.InitializeTask.InitializeTaskCallback;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
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

        setContentView(R.layout.activity_splash);

        mInitializeTask = new InitializeTask(this, this);

        mInitializeTask.execute();
    }

    @Override
    protected void onDestroy()
    {
        if (mInitializeTask != null)
        {
            mInitializeTask.cancel(true);
        }

        super.onDestroy();
    }

    @Override
    public void onInitialized(boolean success)
    {
        if (success)
        {
            startActivity(new Intent(this, MainActivity.class));
        }
        else
        {
            //FIXME
            // show message here
        }

        finish();
    }
}
