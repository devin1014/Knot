package com.android.smartlink.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;

public class WelcomeActivity extends AppCompatActivity implements OnClickListener
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        findViewById(R.id.demo_mode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        finish();

        switch (v.getId())
        {
            case R.id.demo_mode:

                AppManager.getInstance().setDemoMode(true);

                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }
}
