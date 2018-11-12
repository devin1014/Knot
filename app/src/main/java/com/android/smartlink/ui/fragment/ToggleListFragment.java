package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.Constants.MODULE_FLAG;
import com.android.smartlink.R;
import com.android.smartlink.assist.BaseExecutorService;
import com.android.smartlink.assist.eventbus.EventBusMessages.ModuleDataChangedEvent;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.ToggleModuleImp;
import com.android.smartlink.ui.widget.adapter.ToggleAdapterTablet;
import com.android.smartlink.util.UIConverter;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 15:04
 */
public class ToggleListFragment extends BaseSmartlinkFragment
{
    public static ToggleListFragment newInstance(Bundle arguments, int index)
    {
        arguments.putInt(Constants.KEY_EXTRA_TOGGLE_INDEX, index);
        ToggleListFragment fragment = new ToggleListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.toggle_recycler_view)
    RecyclerView mRecyclerView;

    private ToggleAdapterTablet mAdapter;

    private int mPageIndex = 0;

    private BaseExecutorService mExecutorService = new BaseExecutorService();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_toggle_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mPageIndex = getArguments().getInt(Constants.KEY_EXTRA_TOGGLE_INDEX);

        ModulesData modules = (ModulesData) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        mAdapter = new ToggleAdapterTablet(getLayoutInflater(), mToggleOnItemClickListener);

        mAdapter.setData(parseList(modules));

        mRecyclerView.setAdapter(mAdapter);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModuleDataChangedEvent(ModuleDataChangedEvent event)
    {
        if (mAdapter != null)
        {
            mAdapter.setData(parseList(event.modulesData));
        }
    }

    //    @Override
    //    public void notifyModulesChanged(ModulesData modules)
    //    {
    //        if (mAdapter != null)
    //        {
    //            mAdapter.setData(parseList(modules));
    //        }
    //    }

    private List<ToggleModuleImp> parseList(@Nullable ModulesData modules)
    {
        if (modules == null || modules.getToggleModules() == null)
        {
            return null;
        }

        return UIConverter.convertToggle(modules.getToggleModules(), mPageIndex * ToggleFragment.MAX_TOGGLE_SIZE, ToggleFragment.MAX_TOGGLE_SIZE);
    }

    private OnItemClickListener<ToggleModuleImp> mToggleOnItemClickListener = new OnItemClickListener<ToggleModuleImp>()
    {
        @Override
        public void onItemClick(View view, ToggleModuleImp toggleModuleImp)
        {
            boolean isToggleOn = toggleModuleImp.isToggleOn();

            toggleModuleImp.setToggleOn(!isToggleOn);

            int value = isToggleOn ? MODULE_FLAG.CTRL_OFF.value : MODULE_FLAG.CTRL_ON.value;

            mExecutorService.execute(toggleModuleImp.getId(), toggleModuleImp.getDeviceId(), value);

            view.setSelected(!isToggleOn);
        }
    };
}
