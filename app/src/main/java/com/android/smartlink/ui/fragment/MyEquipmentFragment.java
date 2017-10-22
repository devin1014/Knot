package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.CommonItemDecoration;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.widget.adapter.EquipmentAdapter;
import com.android.smartlink.util.ConvertUtil;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-22
 * Time: 13:42
 */
public class MyEquipmentFragment extends BaseSmartlinkFragment
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.settings_margin)));

        mAdapter = new EquipmentAdapter(getActivity().getLayoutInflater());

        mAdapter.setData(ConvertUtil.convertModule(AppManager.getInstance().getModules(), false));

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onEditClick(boolean selected)
    {
        mAdapter.setEditMode(selected);
    }
}
