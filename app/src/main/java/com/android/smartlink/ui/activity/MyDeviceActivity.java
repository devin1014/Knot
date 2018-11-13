package com.android.smartlink.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.DevicesFragment;

/**
 * User: LIUWEI
 * Date: 2017-10-24
 * Time: 17:56
 */
public class MyDeviceActivity extends BaseSmartlinkActivity
{
    public static void startActivity(Activity activity, String title)
    {
        Intent intent = new Intent(activity, MyDeviceActivity.class);

        intent.putExtra(Constants.KEY_EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_my_device;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        mNavigationComposite.showPrimaryFragment(new DevicesFragment(), getIntent().getStringExtra(Constants.KEY_EXTRA_TITLE));
    }

    public void toEditMode()
    {
        if (!getEditButton().isSelected())
        {
            onNavEditClick();
        }
    }
}
