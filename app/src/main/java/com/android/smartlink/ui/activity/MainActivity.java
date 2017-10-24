package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.HomeFragment;
import com.android.smartlink.ui.fragment.SettingsFragment;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment.OnFragmentCallback;
import com.android.smartlink.util.ViewUtil;

import butterknife.BindView;

public class MainActivity extends BaseSmartlinkActivity implements OnTabSelectedListener, OnFragmentCallback
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
        //init menus
        //noinspection deprecation
        mTabLayout.setOnTabSelectedListener(this);

        String[] menus = getResources().getStringArray(R.array.menus_icon);

        for (int i = 0; i < menus.length; i++)
        {
            String menu = menus[i];

            Tab tab = mTabLayout.newTab();

            tab.setIcon(ViewUtil.getDrawable(this, menu));

            mTabLayout.addTab(tab, i == 0);
        }
    }

    @Override
    public void onFragmentsChanged()
    {
        super.onFragmentsChanged();

        mToolbarTitle.setText(mNavigationComposite.getTitle());
    }

    @Override
    public void showDetailFragment(Fragment fragment, String title, int mode)
    {
        mNavigationComposite.showSecondaryFragment(fragment, title);

        setEditMode(mode);
    }

    @Override
    public void onTabSelected(Tab tab)
    {
        switch (tab.getPosition())
        {
            case POS_MAIN:

                mNavigationComposite.showPrimaryFragment(new HomeFragment(), R.string.menu_main);

                break;

            case POS_EVENTS:

                mNavigationComposite.showPrimaryFragment(EventsFragment.newInstance(), R.string.menu_events);

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
