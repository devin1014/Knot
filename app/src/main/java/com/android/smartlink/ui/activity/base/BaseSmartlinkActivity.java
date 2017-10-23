package com.android.smartlink.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.FragmentNavigationComposite;
import com.android.smartlink.assist.FragmentNavigationComposite.FragmentNavigationCompositeCallback;
import com.android.smartlink.ui.fragment.EventsFragment;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-15
 * Time: 14:24
 */
public abstract class BaseSmartlinkActivity extends AppCompatActivity implements FragmentNavigationCompositeCallback
{
    @BindView(R.id.toolbar_nav_back)
    ImageView mNavigationIcon;

    @BindView(R.id.toolbar_nav_edit)
    ImageView mEditIcon;

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
        mNavigationIcon.setVisibility(mNavigationComposite.isPrimaryMode() ? View.GONE : View.VISIBLE);

        if (mNavigationComposite.isPrimaryMode())
        {
            setEditMode((mNavigationComposite.getCurrentFragment() instanceof EventsFragment) ? Constants.MODE_FILTER : Constants.MODE_NORMAL);
        }
    }

    @OnClick(R.id.toolbar_nav_back)
    public void onNavBackClick()
    {
        onBackPressed();
    }

    @OnClick(R.id.toolbar_nav_edit)
    public void onNavEditClick()
    {
        mEditIcon.setSelected(!mEditIcon.isSelected());

        ((BaseSmartlinkFragment) mNavigationComposite.getCurrentFragment()).onEditClick(mEditIcon.isSelected());
    }

    protected void setEditMode(int mode)
    {
        mEditIcon.setImageLevel(mode);

        if (mode == Constants.MODE_NORMAL)
        {
            mEditIcon.setSelected(false);

            mEditIcon.setVisibility(View.GONE);
        }
        else
        {
            mEditIcon.setVisibility(View.VISIBLE);
        }
    }
}
