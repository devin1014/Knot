package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.eventbus.EventBusMessages.EditModuleEvent;
import com.android.smartlink.ui.activity.MyDeviceActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIDeviceImp;
import com.android.smartlink.ui.widget.adapter.MyDeviceAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-22
 * Time: 13:42
 */
public class DevicesFragment extends BaseSmartlinkFragment implements OnItemClickListener<UIDeviceImp>
{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MyDeviceAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mAdapter = new MyDeviceAdapter(getActivity().getLayoutInflater(), this);

        mAdapter.setData(ConvertUtil.convertEquipment(AppManager.getInstance().getAllModules()));

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

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditModuleEvent(EditModuleEvent event)
    {
        mAdapter.setEditMode(event.edited);
    }

    @Override
    public void onEditClick(boolean selected)
    {
        mAdapter.setEditMode(selected);
    }

    @Override
    public void onItemClick(View view, UIDeviceImp uiModuleImp)
    {
        if (getActivity() instanceof MyDeviceActivity)
        {
            ((MyDeviceActivity) getActivity()).toEditMode();
        }
    }
}
