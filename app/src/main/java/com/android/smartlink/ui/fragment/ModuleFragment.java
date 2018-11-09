package com.android.smartlink.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.ui.fragment.base.BaseModulesFragment;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.widget.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 13:54
 */
public class ModuleFragment extends BaseSmartlinkFragment
{
    public static ModuleFragment newInstance(ModulesData modules)
    {
        ModuleFragment fragment = new ModuleFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.KEY_EXTRA_MODULES, modules);
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.model_pager)
    ViewPager mViewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_module, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mViewPager.setAdapter(new ModuleAdapter(getChildFragmentManager()));

        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(mViewPager.getAdapter().getCount());
        scaleCircleNavigator.setNormalCircleColor(Color.parseColor("#aaffffff"));
        scaleCircleNavigator.setSelectedCircleColor(Color.WHITE);
        scaleCircleNavigator.setMaxRadius(3);
        scaleCircleNavigator.setMinRadius(2);
        mMagicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    public void notifyModulesChanged(ModulesData modules)
    {
        if (mViewPager != null && mViewPager.getAdapter() != null)
        {
            Fragment fragment = ((ModuleAdapter) mViewPager.getAdapter()).getItem(0);

            if (fragment instanceof ModuleStatusFragment)
            {
                ((ModuleStatusFragment) fragment).notifyModulesChanged(modules);
            }
        }
    }

    private class ModuleAdapter extends FragmentPagerAdapter
    {
        private SparseArray<BaseModulesFragment> mFragments;

        ModuleAdapter(FragmentManager fm)
        {
            super(fm);
            mFragments = new SparseArray<>();
        }

        @Override
        public Fragment getItem(int position)
        {
            BaseModulesFragment fragment = mFragments.get(position);

            if (fragment != null)
            {
                return fragment;
            }

            if (position == 0)
            {
                fragment = ModuleStatusFragment.newInstance(getArguments());
            }
            else
            {
                fragment = new ModuleChartFragment();
            }

            mFragments.put(position, fragment);

            return fragment;
        }

        @Override
        public int getCount()
        {
            return 2;
        }
    }
}
