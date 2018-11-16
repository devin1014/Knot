package com.android.smartlink.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.widget.adapter.MyFragmentPagerAdapter;
import com.android.smartlink.util.FragmentUtils;
import com.android.smartlink.util.ui.MagicIndicatorHelper;

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
    @BindView(R.id.model_pager)
    ViewPager mViewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
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
        mViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        mMagicIndicator.setNavigator(MagicIndicatorHelper.newScaleCircleNavigator(getActivity(),
                mViewPager.getAdapter().getCount(),
                new int[]{Color.parseColor("#ccffffff"), Color.parseColor("#ffffffff")},
                new int[]{2, 4}));

        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private class MyAdapter extends MyFragmentPagerAdapter
    {
        MyAdapter(FragmentManager fm)
        {
            super(fm, 2);
        }

        @Override
        public Fragment createFragment(int position)
        {
            if (position == 0)
            {
                return FragmentUtils.newInstance(ModuleStatusFragment.class, getArguments());
            }
            else
            {
                return new ModuleChartFragment();
            }
        }
    }
}