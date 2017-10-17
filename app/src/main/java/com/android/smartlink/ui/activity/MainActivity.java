package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.SettingsFragment;
import com.android.smartlink.ui.fragment.SmartlinkFragment;
import com.android.smartlink.util.ViewUtil;

import butterknife.BindView;

public class MainActivity extends BaseSmartlinkActivity implements OnTabSelectedListener
{
    private static final int POS_MAIN = 0;

    private static final int POS_EVENTS = 1;

    private static final int POS_SETTINGS = 2;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

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
        String[] menus = getResources().getStringArray(R.array.menus_icon);

        for (String menu : menus)
        {
            Tab tab = mTabLayout.newTab();

            tab.setIcon(ViewUtil.getDrawable(this, menu));

            mTabLayout.addTab(tab);
        }

        //noinspection deprecation
        mTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onFragmentsChanged()
    {
        mToolbarTitle.setText(mNavigationComposite.getTitle());
    }

    @Override
    public void onTabSelected(Tab tab)
    {
        switch (tab.getPosition())
        {
            case POS_MAIN:

                mNavigationComposite.showPrimaryFragment(new SmartlinkFragment(), R.string.menu_main);

                break;

            case POS_EVENTS:

                mNavigationComposite.showPrimaryFragment(new EventsFragment(), R.string.menu_events);

                break;

            case POS_SETTINGS:

                mNavigationComposite.showPrimaryFragment(new SettingsFragment(), R.string.menu_settings);

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
