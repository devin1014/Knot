package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.neulion.android.diffrecycler.DiffRecyclerAdapter;
import com.neulion.android.diffrecycler.holder.DiffViewHolder;
import com.neulion.android.diffrecycler.listener.OnItemClickListener;

/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends DiffRecyclerAdapter<UIMonitorModule>
{
    public ModuleAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIMonitorModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getViewHolderLayout(int type)
    {
        //int mainModuleType = hasHeaders() ? 1 : 0;

        if (type == 0) // first item always main module
        {
            return R.layout.adapter_module_main;
        }

        return R.layout.adapter_module;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public void onBindViewHolder(DiffViewHolder<UIMonitorModule> holder, UIMonitorModule monitorModule, int i)
    {
        holder.getViewDataBindingInterface().executePendingBindings(monitorModule);
    }
}
