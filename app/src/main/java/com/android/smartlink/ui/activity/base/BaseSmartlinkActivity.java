package com.android.smartlink.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.assist.FragmentNavigationComposite;
import com.android.smartlink.assist.FragmentNavigationComposite.FragmentNavigationCompositeCallback;
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
    ImageView mNavigationButton;

    @BindView(R.id.toolbar_nav_edit)
    ImageView mEditButton;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

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
        mToolbarTitle.setText(mNavigationComposite.getTitle());
    }

    @OnClick(R.id.toolbar_nav_back)
    public void onNavBackClick()
    {
        onBackPressed();
    }

    @OnClick(R.id.toolbar_nav_edit)
    public void onNavEditClick()
    {
        mEditButton.setSelected(!mEditButton.isSelected());

        ((BaseSmartlinkFragment) mNavigationComposite.getCurrentFragment()).onEditClick(mEditButton.isSelected());
    }

    protected void setEditButtonVisibility(boolean show)
    {
        mEditButton.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
