package com.android.smartlink.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.smartlink.assist.FragmentNavigationComposite;
import com.android.smartlink.assist.FragmentNavigationComposite.FragmentNavigationCompositeCallback;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-15
 * Time: 14:24
 */
public abstract class BaseSmartlinkActivity extends AppCompatActivity implements FragmentNavigationCompositeCallback
{
    protected abstract int getLayoutId();

    private Unbinder mButterKnife;

    protected FragmentNavigationComposite mNavigationComposite;

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
    public void onBackPressed()
    {
        if (mNavigationComposite.onBackPressed())
        {
            return;
        }

        //// TODO: 2017/10/16
        finish();
        //super.onBackPressed();
    }

    @Override
    public void onFragmentsChanged()
    {
    }

}
