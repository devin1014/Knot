package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.CommonItemDecoration;
import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter;
import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter.OnItemClickListener;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UISetting;
import com.android.smartlink.ui.widget.adapter.SettingsAdapter;
import com.android.smartlink.util.ConvertUtil;

import butterknife.BindView;
import butterknife.OnClick;

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

        initComponent();
    }

    private void initComponent()
    {
        SettingsAdapter adapter;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.home_list_divider)));

        mRecyclerView.setAdapter(adapter = new SettingsAdapter(getActivity().getLayoutInflater(), mOnItemClickListener));

        adapter.setData(ConvertUtil.convertSettings(getResources().getStringArray(R.array.settings),

                getResources().getStringArray(R.array.settings_image)));
    }

    private OnItemClickListener<UISetting> mOnItemClickListener = new OnItemClickListener<UISetting>()
    {
        @Override
        public void onItemClick(DataBindingAdapter<UISetting> adapter, View view, UISetting setting, int position)
        {
            switch (position)
            {
                case Constants.POS_SETTINGS_MY_EQUIPMENT:

                    showDetailFragment(new MyEquipmentFragment(), setting.getName(), Constants.MODE_EDIT);

                    break;

                case Constants.POS_SETTINGS_FEEDBACK:

                    break;

                case Constants.POS_SETTINGS_TERMS:

                    break;

                case Constants.POS_SETTINGS_ABOUT:

                    break;
            }
        }
    };

    @OnClick(R.id.settings_signout)
    public void signOut()
    {
        //// TODO: 2017/10/22
    }
}
