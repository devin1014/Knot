package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.ui.fragment.base.BaseModulesFragment;
import com.android.smartlink.ui.widget.adapter.ModuleAdapterTablet;
import com.android.smartlink.util.UIConverter;
import com.neulion.core.widget.recyclerview.RecyclerView;

import butterknife.BindView;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-20
 * Time: 14:03
 */
public class ModuleStatusFragment extends BaseModulesFragment
{
    public static ModuleStatusFragment newInstance(Bundle arguments)
    {
        ModuleStatusFragment fragment = new ModuleStatusFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.module_status_recycler_view)
    RecyclerView mRecyclerView;

    ModuleAdapterTablet mModuleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
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
        Modules modules = (Modules) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        mModuleAdapter = new ModuleAdapterTablet(getLayoutInflater());

        mModuleAdapter.setData(UIConverter.convertModules(modules != null ? modules.getModules() : null));

        mRecyclerView.setAdapter(mModuleAdapter);

    }

    @Override
    public void notifyModulesChanged(Modules modules)
    {
        mModuleAdapter.setData(UIConverter.convertModules(modules != null ? modules.getModules() : null));
    }
}
