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
import com.android.smartlink.bean.Modules;
import com.android.smartlink.ui.fragment.base.BaseModulesFragment;
import com.android.smartlink.ui.model.UIToggle;
import com.android.smartlink.ui.widget.adapter.ToggleAdapterTablet;
import com.android.smartlink.util.UIConverter;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter.OnItemClickListener;

import java.util.List;

import butterknife.BindView;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-20
 * Time: 15:04
 */
public class ToggleListFragment extends BaseModulesFragment
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

        Modules modules = (Modules) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        mAdapter = new ToggleAdapterTablet(getLayoutInflater(), mToggleOnItemClickListener);

        mAdapter.setData(parseList(modules));

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void notifyModulesChanged(Modules modules)
    {
        if (mAdapter != null)
        {
            mAdapter.setData(parseList(modules));
        }
    }

    private List<UIToggle> parseList(@Nullable Modules modules)
    {
        if (modules == null || modules.getToggles() == null)
        {
            return null;
        }

        return UIConverter.convertToggle(modules.getToggles(), mPageIndex * ToggleFragment.MAX_TOGGLE_SIZE, ToggleFragment.MAX_TOGGLE_SIZE);
    }

    private OnItemClickListener<UIToggle> mToggleOnItemClickListener = new OnItemClickListener<UIToggle>()
    {
        @Override
        public void onItemClick(DataBindingAdapter<UIToggle> dataBindingAdapter, View view, UIToggle uiToggle, int i)
        {
            boolean isToggleOn = view.isSelected();

            int value = isToggleOn ? MODULE_FLAG.CTRL_OFF.value : MODULE_FLAG.CTRL_ON.value;

            mExecutorService.execute(uiToggle.getId(), value);

            view.setSelected(!isToggleOn);
        }
    };
}
