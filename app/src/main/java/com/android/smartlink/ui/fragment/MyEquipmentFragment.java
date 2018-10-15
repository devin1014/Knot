package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.activity.EquipmentActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIEquipment;
import com.android.smartlink.ui.widget.adapter.EquipmentAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-22
 * Time: 13:42
 */
public class MyEquipmentFragment extends BaseSmartlinkFragment implements OnItemClickListener<UIEquipment>
{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private EquipmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_my_equipment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mAdapter = new EquipmentAdapter(getActivity().getLayoutInflater(), this);

        mAdapter.setData(ConvertUtil.convertEquipment(AppManager.getInstance().getEquipments()));

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MobclickAgent.onPageStart("Equipment");
    }

    @Override
    public void onPause()
    {
        MobclickAgent.onPageEnd("Equipment");

        super.onPause();
    }

    @Override
    public void onEditClick(boolean selected)
    {
        mAdapter.setEditMode(selected);
    }

    @Override
    public void onItemClick(DataBindingAdapter<UIEquipment> adapter, View view, UIEquipment item, int position)
    {
        if (getActivity() instanceof EquipmentActivity)
        {
            ((EquipmentActivity) getActivity()).toEditMode();
        }
    }
}
