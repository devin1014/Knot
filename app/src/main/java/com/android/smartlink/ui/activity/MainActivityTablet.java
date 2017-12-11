package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

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
}
