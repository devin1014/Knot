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
import com.android.smartlink.bean.Modules;
import com.android.smartlink.ui.fragment.base.BaseModulesFragment;
import com.android.smartlink.ui.widget.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import butterknife.BindView;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-20
 * Time: 14:59
 */
public class ToggleFragment extends BaseModulesFragment
{
    public static final int MAX_TOGGLE_SIZE = 3;

    public static ToggleFragment newInstance(Modules modules)
    {
        ToggleFragment fragment = new ToggleFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(Constants.KEY_EXTRA_MODULES, modules);
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.toggle_pager)
    ViewPager mViewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

    private int mPageSize = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_toggle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        Modules modules = (Modules) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        if (modules != null && modules.getToggles() != null)
        {
            mPageSize = modules.getToggles().size() / MAX_TOGGLE_SIZE;

            if (modules.getToggles().size() % MAX_TOGGLE_SIZE != 0)
            {
                mPageSize++;
            }
        }

        mViewPager.setAdapter(new ToggleAdapter(getChildFragmentManager()));


        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(getActivity());
        scaleCircleNavigator.setCircleCount(mViewPager.getAdapter().getCount());
        scaleCircleNavigator.setNormalCircleColor(Color.parseColor("#aaffffff"));
        scaleCircleNavigator.setSelectedCircleColor(Color.WHITE);
        mMagicIndicator.setNavigator(scaleCircleNavigator);
        scaleCircleNavigator.setMaxRadius(3);
        scaleCircleNavigator.setMinRadius(2);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    public void notifyModulesChanged(Modules modules)
    {
        if (mViewPager != null)
        {
            FragmentPagerAdapter adapter = (FragmentPagerAdapter) mViewPager.getAdapter();

            for (int i = 0; i < adapter.getCount(); i++)
            {
                ((ToggleListFragment) adapter.getItem(i)).notifyModulesChanged(modules);
            }
        }
    }

    private class ToggleAdapter extends FragmentPagerAdapter
    {
        private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

        ToggleAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = mFragmentSparseArray.get(position);

            if (fragment != null)
            {
                return fragment;
            }

            Bundle bundle = new Bundle(getArguments());

            fragment = ToggleListFragment.newInstance(bundle, position);

            mFragmentSparseArray.put(position, fragment);

            return fragment;
        }

        @Override
        public int getCount()
        {
            return mPageSize;
        }
    }
}