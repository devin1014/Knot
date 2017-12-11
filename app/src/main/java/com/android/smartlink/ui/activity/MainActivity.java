package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.widget.Toast;

import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.HomeFragment;
import com.android.smartlink.ui.fragment.SettingsFragment;

import butterknife.BindView;

public class MainActivity extends BaseSmartlinkActivity implements OnTabSelectedListener
{
    private static final int POS_MAIN = 0;

    private static final int POS_EVENTS = 1;

    private static final int POS_SETTINGS = 2;

    private int[] MENUS = new int[]{R.drawable.menu_home_selector, R.drawable.menu_events_selector,
            R.drawable.menu_settings_selector};

    private int[] MENUS_TITLE = new int[]{R.string.menu_main, R.string.menu_events, R.string.menu_settings};

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        initComponent();
    }

    private void initComponent()
    {
        //init menus
        mTabLayout.addOnTabSelectedListener(this);

        for (int i = 0; i < MENUS.length; i++)
        {
            Tab tab = mTabLayout.newTab();

            tab.setIcon(MENUS[i]);

            mTabLayout.addTab(tab, i == 0); // default select first one.
        }
    }

    private long mExitTimeStamp;

    @Override
    public void onBackPressed()
    {
        if (mNavigationComposite.onBackPressed())
        {
            return;
        }

        final long duration = SystemClock.uptimeMillis() - mExitTimeStamp;

        if (duration >= 2500)
        {
            mExitTimeStamp = SystemClock.uptimeMillis();

            Toast.makeText(this, getString(R.string.confirm_exit_app), Toast.LENGTH_SHORT).show();
        }
        else
        {
            finish();
        }
    }

    @Override
    public void onFragmentsChanged()
    {
        super.onFragmentsChanged();

        mExitTimeStamp = 0L;
    }

    @Override
    public void onTabSelected(Tab tab)
    {
        switch (tab.getPosition())
        {
            case POS_MAIN:

                mNavigationComposite.showPrimaryFragment(new HomeFragment(), MENUS_TITLE[POS_MAIN]);

                setEditButtonVisibility(false);

                break;

            case POS_EVENTS:

                mNavigationComposite.showPrimaryFragment(EventsFragment.newInstance(), MENUS_TITLE[POS_EVENTS]);

                setEditButtonVisibility(true);

                break;

            case POS_SETTINGS:

                mNavigationComposite.showPrimaryFragment(new SettingsFragment(), MENUS_TITLE[POS_SETTINGS]);

                setEditButtonVisibility(false);

                break;
        }
    }

    @Override
    public void onTabUnselected(Tab tab)
    {
    }

    @Override
    public void onTabReselected(Tab tab)
    {
    }

}
