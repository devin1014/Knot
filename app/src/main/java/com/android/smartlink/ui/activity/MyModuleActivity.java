package com.android.smartlink.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.MyModulesFragment;

/**
 * User: LIUWEI
 * Date: 2017-10-24
 * Time: 17:56
 */
public class MyModuleActivity extends BaseSmartlinkActivity
{
    public static void startActivity(Activity activity, String title)
    {
        Intent intent = new Intent(activity, MyModuleActivity.class);

        intent.putExtra(Constants.KEY_EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_my_module;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        mNavigationComposite.showPrimaryFragment(new MyModulesFragment(), getIntent().getStringExtra(Constants.KEY_EXTRA_TITLE));
    }

    public void toEditMode()
    {
        if (!getEditButton().isSelected())
        {
            onNavEditClick();
        }
    }
}
