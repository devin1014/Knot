package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.ui.fragment.base.BaseModulesFragment;
import com.android.smartlink.ui.model.IModule.ImageType;
import com.android.smartlink.ui.widget.adapter.ModuleAdapterTablet;
import com.android.smartlink.util.AppDataBindingAdapter;
import com.android.smartlink.util.UIConverter;
import com.neulion.core.widget.recyclerview.RecyclerView;

import java.util.List;

import butterknife.BindView;

/**
 * User: liuwei
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
    @BindView(R.id.home_main_module)
    View mMainModule;

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
        ModulesData modules = (ModulesData) getArguments().getSerializable(Constants.KEY_EXTRA_MODULES);

        List<MonitorModuleData> list = modules.getMonitorModules();

        mModuleAdapter = new ModuleAdapterTablet(getLayoutInflater());

        mModuleAdapter.setData(UIConverter.convertModules(list, 1, list.size(), ImageType.DRAWABLE_NORMAL_LIGHT));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setAdapter(mModuleAdapter);

        AppDataBindingAdapter.viewBinding(mMainModule, BR.data, UIConverter.convertModule(list, 0, ImageType.DRAWABLE_LARGE_LIGHT));
    }

    @Override
    public void notifyModulesChanged(ModulesData modules)
    {
        List<MonitorModuleData> list = modules.getMonitorModules();

        mModuleAdapter.setData(UIConverter.convertModules(list, 1, list.size(), ImageType.DRAWABLE_NORMAL_LIGHT));

        AppDataBindingAdapter.viewBinding(mMainModule, BR.data, UIConverter.convertModule(list, 0, ImageType.DRAWABLE_LARGE_LIGHT));
    }
}
