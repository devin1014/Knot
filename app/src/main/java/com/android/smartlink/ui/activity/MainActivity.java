package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.RadioGroup;

import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.MainFragment;
import com.android.smartlink.ui.fragment.SettingsFragment;

import butterknife.BindView;

public class MainActivity extends BaseSmartlinkActivity implements RadioGroup.OnCheckedChangeListener
{
    @BindView(R.id.navigation)
    RadioGroup mNavigation;

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
        mNavigation.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
        switch (checkedId)
        {
            case R.id.navigation_item_main:

                mNavigationComposite.showPrimaryFragment(new MainFragment(), R.string.navigation_item_main);

                break;

            case R.id.navigation_item_events:

                mNavigationComposite.showPrimaryFragment(new EventsFragment(), R.string.navigation_item_events);

                break;

            case R.id.navigation_item_settings:

                mNavigationComposite.showPrimaryFragment(new SettingsFragment(), R.string.navigation_item_settings);

                break;
        }
    }
}
