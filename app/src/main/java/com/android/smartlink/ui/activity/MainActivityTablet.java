package com.android.smartlink.ui.activity;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivityTablet;
import com.android.smartlink.ui.fragment.HomeFragmentTablet;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-05
 * Time: 15:55
 */
public class MainActivityTablet extends BaseSmartlinkActivityTablet
{
    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main_tablet;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        mNavigationComposite.showPrimaryFragment(new HomeFragmentTablet(), "");
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        hideNavigationBar();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        boolean handled = super.dispatchTouchEvent(ev);

        if ((ev.getAction() & ev.getActionMasked()) == MotionEvent.ACTION_DOWN)
        {
            hideNavigationBar();
        }

        return handled;
    }

    public void hideNavigationBar()
    {
        int flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        if (VERSION.SDK_INT >= 16)
        {
            flag = flag | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        }

        getWindow().getDecorView().setSystemUiVisibility(flag);
    }
}
