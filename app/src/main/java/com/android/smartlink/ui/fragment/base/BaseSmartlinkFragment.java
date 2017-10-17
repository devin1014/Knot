package com.android.smartlink.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 17:55
 */
public class BaseSmartlinkFragment extends Fragment
{
    private Unbinder mButterKnife;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mButterKnife = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView()
    {
        mButterKnife.unbind();

        super.onDestroyView();
    }

    public boolean onBackPressed()
    {
        return false;
    }
}
