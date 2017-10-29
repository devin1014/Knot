package com.android.smartlink.ui.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;

import com.android.smartlink.R;
import com.android.smartlink.assist.InitializeTask;
import com.android.smartlink.assist.InitializeTask.InitializeTaskCallback;
import com.android.smartlink.util.TestUtil;

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

        TestUtil.set();

        setContentView(R.layout.activity_splash);

        TestUtil.test(this, "onCreate");

        (mInitializeTask = new InitializeTask(this, this)).execute();
    }

    @Override
    protected void onDestroy()
    {
        mInitializeTask.destroy();

        super.onDestroy();
    }

    @Override
    public void onInitialized(boolean success)
    {
        if (success)
        {
            finish();

            startActivity(new Intent(this, MainActivity.class));
        }
        else
        {
            AlertDialog.Builder builder = new Builder(this);

            builder.setCancelable(false);

            builder.setMessage(R.string.app_launch_failed);

            builder.setPositiveButton(R.string.ui_ok, new OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();

                    finish();
                }
            });

            builder.show();
        }
    }
}
