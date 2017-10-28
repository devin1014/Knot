package com.android.smartlink.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.activity.base.BaseSmartlinkActivity;
import com.android.smartlink.ui.fragment.AboutFragment;
import com.android.smartlink.ui.fragment.TermsFragment;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-28
 * Time: 16:06
 */
public class SingleActivity extends BaseSmartlinkActivity
{
    private static final int PAGE_TERMS = 1;

    private static final int PAGE_ABOUT = 2;

    public static void startTermsActivity(Activity activity, String title)
    {
        Intent intent = new Intent(activity, SingleActivity.class);

        intent.putExtra(Constants.KEY_EXTRA_PAGE, PAGE_TERMS);

        intent.putExtra(Constants.KEY_EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    public static void startAboutActivity(Activity activity, String title)
    {
        Intent intent = new Intent(activity, SingleActivity.class);

        intent.putExtra(Constants.KEY_EXTRA_PAGE, PAGE_ABOUT);

        intent.putExtra(Constants.KEY_EXTRA_TITLE, title);

        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_single;
    }

    @Override
    protected void onActivityCreate(@Nullable Bundle savedInstanceState)
    {
        String title = getIntent().getStringExtra(Constants.KEY_EXTRA_TITLE);

        int page = getIntent().getIntExtra(Constants.KEY_EXTRA_PAGE, PAGE_TERMS);

        if (page == PAGE_TERMS)
        {
            mNavigationComposite.showPrimaryFragment(new TermsFragment(), title);
        }
        else if (page == PAGE_ABOUT)
        {
            mNavigationComposite.showPrimaryFragment(new AboutFragment(), title);
        }
    }
}
