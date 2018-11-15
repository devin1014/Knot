package com.android.smartlink.ui.widget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

public abstract class MyFragmentPagerAdapter extends FragmentStatePagerAdapter
{
    private SparseArray<Fragment> mFragments;

    private final int mFragmentCount;

    public MyFragmentPagerAdapter(FragmentManager fm, int count)
    {
        super(fm);

        mFragments = new SparseArray<>();

        mFragmentCount = count;
    }

    @Override
    public Fragment getItem(int i)
    {
        Fragment fragment = mFragments.get(i);

        if (fragment == null)
        {
            fragment = createFragment(i);

            mFragments.put(i, fragment);
        }

        return fragment;
    }

    public abstract Fragment createFragment(int position);

    @SuppressWarnings("unused")
    public void destroyFragment(int position)
    {
        mFragments.remove(position);
    }

    @Override
    public int getCount()
    {
        return mFragmentCount;
    }
}
