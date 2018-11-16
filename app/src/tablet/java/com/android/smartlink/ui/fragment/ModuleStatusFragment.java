package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.eventbus.EventBusMessages.EventModuleDataChanged;
import com.android.smartlink.assist.eventbus.EventBusMessages.EventModuleGroupChanged;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.databinding.ItemHomeMainModuleBindingImpl;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.BaseModule.Module.GroupType;
import com.android.smartlink.ui.model.BaseModule.Module.ImageType;
import com.android.smartlink.ui.widget.adapter.ModuleAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.core.widget.recyclerview.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 14:03
 */
public class ModuleStatusFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.module_status_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_main_module)
    View mMainModule;

    private ItemHomeMainModuleBindingImpl mMainModuleBinding;
    private ModuleAdapter mModuleAdapter;
    private ModulesData mModulesData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_module_status, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mModulesData = (ModulesData) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false));

        mRecyclerView.setAdapter(mModuleAdapter = new ModuleAdapter(getLayoutInflater()));

        mMainModuleBinding = DataBindingUtil.bind(mMainModule);

        resetData();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModuleDataChangedEvent(EventModuleDataChanged event)
    {
        mModulesData = event.modulesData;

        resetData();
    }

    private int mGroupId = GroupType.GROUP_ALL;

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModuleGroupChangedEvent(EventModuleGroupChanged event)
    {
        mGroupId = event.group;

        resetData();
    }

    private void resetData()
    {
        List<MonitorModuleData> list = mModulesData.getMonitorModules();

        int group = mGroupId;

        //reset main module
        mMainModuleBinding.setData(ConvertUtil.convertModule(list.get(0), ImageType.DRAWABLE_LARGE_LIGHT));
        mMainModuleBinding.executePendingBindings();

        //reset others
        mModuleAdapter.setData(ConvertUtil.convertModules(list, 1, list.size(), ImageType.DRAWABLE_NORMAL_LIGHT, group));
    }
}
