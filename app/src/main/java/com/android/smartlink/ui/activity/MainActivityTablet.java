package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout.OnTabSelectedListener;

import com.android.devin.core.util.Debug;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.application.manager.AppManager.RequestMode;
import com.android.smartlink.util.Utils;

/**
 * User: LIUWEI
 * Date: 2017-12-05
 * Time: 15:55
 */
public class MainActivityTablet extends MainActivity implements OnTabSelectedListener
{
    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreate(savedInstanceState);

        final boolean debug = Utils.isDevDebugMode(this);

        Debug.init(debug);

        AppManager.getInstance().setDemoMode(debug);

        AppManager.getInstance().setDemoModeStatus(-1);

        AppManager.getInstance().setRequestMode(debug ? RequestMode.MODE_HTTP : RequestMode.MODE_REMOTE);
    }

}