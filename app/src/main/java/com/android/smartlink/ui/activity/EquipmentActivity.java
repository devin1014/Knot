package com.android.smartlink.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.MyEquipmentFragment;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-24
 * Time: 17:56
 */
public class EquipmentActivity extends BaseSmartlinkActivity
{
    public static void startActivity(Activity activity, String title)
    {
        Intent intent = new Intent(activity, EquipmentActivity.class);

        intent.putExtra(Constants.KEY_EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_equipment;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        mNavigationComposite.showPrimaryFragment(new MyEquipmentFragment(), getIntent().getStringExtra(Constants.KEY_EXTRA_TITLE));
    }
}
