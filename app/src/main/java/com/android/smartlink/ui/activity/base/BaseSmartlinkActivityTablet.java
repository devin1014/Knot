package com.android.smartlink.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.smartlink.assist.FragmentNavigationComposite;
import com.android.smartlink.assist.FragmentNavigationComposite.FragmentNavigationCompositeCallback;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-05
 * Time: 16:21
 */
public abstract class BaseSmartlinkActivityTablet extends AppCompatActivity implements FragmentNavigationCompositeCallback
{
    private Unbinder mButterKnife;

    protected FragmentNavigationComposite mNavigationComposite;

    protected abstract int getLayoutId();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        mButterKnife = ButterKnife.bind(this);

        mNavigationComposite = new FragmentNavigationComposite(this, getSupportFragmentManager(), this);

        onActivityCreate(savedInstanceState);
    }

    protected abstract void onActivityCreate(@Nullable Bundle savedInstanceState);

    @Override
    protected final void onDestroy()
    {
        mButterKnife.unbind();

        mNavigationComposite.destroy();

        onActivityDestroy();

        super.onDestroy();
    }

    protected void onActivityDestroy()
    {
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause()
    {
        MobclickAgent.onPause(this);

        super.onPause();
    }

    @Override
    public void onBackPressed()
    {
        if (mNavigationComposite.onBackPressed())
        {
            return;
        }

        finish();
    }

    @Override
    public void onFragmentsChanged()
    {
    }

}
