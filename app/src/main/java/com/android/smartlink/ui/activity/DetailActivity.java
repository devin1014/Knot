package com.android.smartlink.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.DetailFragment;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment.OnFragmentCallback;
import com.android.smartlink.ui.model.UIModule;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-24
 * Time: 16:56
 */
public class DetailActivity extends BaseSmartlinkActivity implements OnFragmentCallback
{
    public static void startActivity(Activity activity, String title, UIModule module)
    {
        Intent intent = new Intent(activity, DetailActivity.class);

        intent.putExtra(Constants.KEY_EXTRA_TITLE, title);

        intent.putExtra(Constants.KEY_EXTRA_UI_MODULE, module);

        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_detail;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        mNavigationComposite.showPrimaryFragment(DetailFragment.newInstance(getIntent().getExtras()), getIntent().getStringExtra(Constants.KEY_EXTRA_TITLE));
    }

    @Override
    public void showDetailFragment(Fragment fragment, String title)
    {
        mNavigationComposite.showSecondaryFragment(fragment, title);
    }
}
