package com.android.smartlink.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;

import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.HomeFragment;
import com.android.smartlink.ui.fragment.HomeFragmentTablet;
import com.android.smartlink.ui.fragment.SettingsFragment;
import com.android.smartlink.ui.fragment.SettingsFragmentTablet;

import java.io.Serializable;

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

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T newInstance(Class<T> cls, Pair<String, ? extends Serializable>... pairs)
    {
        Bundle arguments = new Bundle();

        for (Pair<String, ? extends Serializable> p : pairs)
        {
            arguments.putSerializable(p.first, p.second);
        }

        return newInstance(cls, arguments);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Fragment> T newInstance(Class<T> cls, Bundle arguments)
    {
        T fragment;
        try
        {
            fragment = cls.newInstance();

            fragment.setArguments(arguments);

            return fragment;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();

            fragment = (T) new Fragment();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();

            fragment = (T) new Fragment();
        }

        return fragment;
    }
}
