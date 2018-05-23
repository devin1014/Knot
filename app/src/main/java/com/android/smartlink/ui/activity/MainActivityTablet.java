package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout.OnTabSelectedListener;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.application.manager.AppManager.MODE;
import com.android.smartlink.util.Utils;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-05
 * Time: 15:55
 */
public class MainActivityTablet extends MainActivity implements OnTabSelectedListener
{
    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreate(savedInstanceState);

        AppManager.getInstance().setDemoMode(true);

        AppManager.getInstance().setDemoModeStatus(-1);

        AppManager.getInstance().setRequestMode(Utils.isDevDebugMode(this) ? MODE.MODE_HTTP : MODE.MODE_LOCAL);
    }

}