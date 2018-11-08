package com.android.smartlink.util;

import android.support.v4.app.Fragment;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.HomeFragment;
import com.android.smartlink.ui.fragment.HomeFragmentTablet;
import com.android.smartlink.ui.fragment.SettingsFragment;
import com.android.smartlink.ui.fragment.SettingsFragmentTablet;

/**
 * User: liuwei
 * Date: 2018-05-16
 * Time: 23:50
 */
public class FragmentUtils
{
    public static Fragment newHomeFragment()
    {
        return AppManager.getInstance().isPhone() ? new HomeFragment() : new HomeFragmentTablet();
    }

    public static Fragment newEventsFragment(int... ids)
    {
        return EventsFragment.newInstance(ids);
    }

    public static Fragment newSettingsFragment()
    {
        return AppManager.getInstance().isPhone() ? new SettingsFragment() : new SettingsFragmentTablet();
    }
}
