package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.R;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.widget.modulestatus.ModuleStatusLayout;
import com.neulion.core.widget.recyclerview.handler.DataBindingHandler;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends BaseAdapter<UIModule>
{
    public ModuleAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<UIModule> onCreateHeadHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i)
    {
        return new HeadHolder(layoutInflater.inflate(R.layout.comp_home_module_status, viewGroup, false), null);
    }

    @Override
    protected void onBindHeadHolder(DataBindingHolder<UIModule> holder, int position, List<Object> payloads)
    {
        super.onBindHeadHolder(holder, position, payloads);
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

    public class HeadHolder extends DataBindingHolder<UIModule>
    {
        private ModuleStatusLayout mModuleStatusLayout;

        HeadHolder(View itemView, DataBindingHandler<UIModule> handler)
        {
            super(itemView, handler);

            mModuleStatusLayout = (ModuleStatusLayout) itemView;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onBindViewHolder(Object object)
        {
            mModuleStatusLayout.setModules((List<Module>) object);
        }

        public ModuleStatusLayout getModuleStatusLayout()
        {
            return mModuleStatusLayout;
        }
    }
}
