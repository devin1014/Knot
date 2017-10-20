package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.devin.core.ui.widget.recyclerview.CommonItemDecoration;
import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter;
import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter.OnItemClickListener;
import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UISetting;
import com.android.smartlink.ui.widget.adapter.SettingsAdapter;
import com.android.smartlink.util.ConvertUtil;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:01
 */
public class SettingsFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);
    }

    private void initComponent(View view)
    {
        SettingsAdapter adapter;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.home_list_divider)));

        mRecyclerView.setAdapter(adapter = new SettingsAdapter(getActivity().getLayoutInflater(), mOnItemClickListener));

        adapter.setData(ConvertUtil.convertSettings(getResources().getStringArray(R.array.settings)));
    }

    private OnItemClickListener<UISetting> mOnItemClickListener = new OnItemClickListener<UISetting>()
    {
        @Override
        public void onItemClick(DataBindingAdapter<UISetting> adapter, View view, UISetting uiSetting, int position)
        {
            Toast.makeText(getActivity(), uiSetting.getName(), Toast.LENGTH_SHORT).show();
        }
    };
}
