package com.android.smartlink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 18:44
 */
public class DetailActivity extends BaseSmartlinkActivity
{
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_detail;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
    }
}
