package com.android.smartlink.ui.widget.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;

import com.android.smartlink.BR;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.IModule;
import com.android.smartlink.ui.widget.modulestatus.ModuleStatusLayout;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends DiffDataBindingAdapter<IModule>
{
    public ModuleAdapter(LayoutInflater layoutInflater, OnItemClickListener<IModule> listener)
    {
        super(layoutInflater, listener);
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
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<IModule> holder, IModule iModule, int i)
    {
        ViewDataBinding binding = holder.getViewDataBinding();
        binding.setVariable(BR.data, iModule);
        binding.setVariable(BR.itemClickListener, this);
        binding.executePendingBindings();
    }

    // ----------------------------------------------------------------
    // HeadHolder
    // ----------------------------------------------------------------
    public static class HeadHolder extends DataBindingHolder<IModule>
    {
        private ModuleStatusLayout mModuleStatusLayout;

        HeadHolder(View itemView, OnItemClickListener<IModule> handler)
        {
            super(itemView, handler);

            mModuleStatusLayout = (ModuleStatusLayout) itemView;
        }

        //        @SuppressWarnings("unchecked")
        //        @Override
        //        public void onBindViewHolder(Object object)
        //        {
        //            mModuleStatusLayout.setModules((List<MonitorModuleData>) object);
        //        }

        public ModuleStatusLayout getModuleStatusLayout()
        {
            return mModuleStatusLayout;
        }
    }
}
