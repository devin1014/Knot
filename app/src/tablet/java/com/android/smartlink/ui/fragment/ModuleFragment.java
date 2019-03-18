package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.assist.eventbus.EventBusMessages.EventModuleGroupChanged;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.android.smartlink.ui.widget.adapter.MyFragmentPagerAdapter;
import com.android.smartlink.util.FragmentUtils;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 13:54
 */
public class ModuleFragment extends BaseSmartlinkFragment implements OnItemClickListener<UIMonitorModule>
{
    @BindView(R.id.module_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.module_group)
    RadioGroup mModuleGroup;
    @BindView(R.id.module_back)
    TextView mBack;
    @BindView(R.id.module_title)
    TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_module, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mBack.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showPrimary();
            }
        });

        mViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        mModuleGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int groupId = 0;

                try
                {
                    groupId = Integer.parseInt((String) group.findViewById(checkedId).getTag());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                EventBus.getDefault().post(new EventModuleGroupChanged(groupId));
            }
        });
    }

    private ModuleChartFragment mModuleChartFragment;

    @Override
    public boolean onBackPressed()
    {
        if (mBack.getVisibility() == View.VISIBLE)
        {
            showPrimary();

            return true;
        }

        return super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, UIMonitorModule uiMonitorModule)
    {
        showDetail(uiMonitorModule);
    }

    public void showPrimary()
    {
        mBack.setVisibility(View.INVISIBLE);

        mTitle.setVisibility(View.VISIBLE);

        mViewPager.setCurrentItem(0, true);

        mModuleGroup.setVisibility(View.VISIBLE);
    }

    public void showDetail(UIMonitorModule module)
    {
        mBack.setText(module.getName());

        mBack.setVisibility(View.VISIBLE);

        mTitle.setVisibility(View.INVISIBLE);

        mViewPager.setCurrentItem(1, true);

        mModuleGroup.setVisibility(View.INVISIBLE);

        if (mModuleChartFragment != null)
        {
            mModuleChartFragment.setModuleData(module);
        }
    }

    private class MyAdapter extends MyFragmentPagerAdapter
    {
        MyAdapter(FragmentManager fm)
        {
            super(fm, 2);
        }

        @Override
        public Fragment createFragment(int position)
        {
            if (position == 0)
            {
                return FragmentUtils.newInstance(ModuleStatusFragment.class, getArguments());
            }
            else
            {
                return mModuleChartFragment = new ModuleChartFragment();
            }
        }
    }

}