package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.R;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.ui.model.IModule;
import com.android.smartlink.ui.widget.modulestatus.ModuleStatusLayout;
import com.neulion.core.widget.recyclerview.handler.DataBindingHandler;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends BaseAdapter<IModule>
{
    public ModuleAdapter(LayoutInflater layoutInflater, OnItemClickListener<IModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<IModule> onCreateHeadHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i)
    {
        return new HeadHolder(layoutInflater.inflate(R.layout.comp_home_module_status, viewGroup, false), null);
    }

    @Override
    protected int getLayout(int type)
    {
        if (type == 1) // first item always main module
        {
            return R.layout.item_home_main_module;
        }

        return R.layout.item_home_module;
    }

    @Override
    public int getViewType(int position)
    {
        return position;
    }

    // ----------------------------------------------------------------
    // HeadHolder
    // ----------------------------------------------------------------
    public static class HeadHolder extends DataBindingHolder<IModule>
    {
        private ModuleStatusLayout mModuleStatusLayout;

        HeadHolder(View itemView, DataBindingHandler<IModule> handler)
        {
            super(itemView, handler);

            mModuleStatusLayout = (ModuleStatusLayout) itemView;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(Object object)
        {
            mModuleStatusLayout.setModules((List<MonitorModuleData>) object);
        }

        public ModuleStatusLayout getModuleStatusLayout()
        {
            return mModuleStatusLayout;
        }
    }
}
