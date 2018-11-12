package com.android.smartlink.ui.widget.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

import com.android.smartlink.BR;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

/**
 * User: LIUWEI
 * Date: 2017-12-25
 * Time: 15:54
 */
public class ModuleAdapterTablet extends DiffDataBindingAdapter<MonitorModuleImp>
{
    public ModuleAdapterTablet(LayoutInflater layoutInflater)
    {
        super(layoutInflater, null);
    }

    @Override
    protected int getLayout(int type)
    {
        return R.layout.item_home_module;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<MonitorModuleImp> holder, MonitorModuleImp monitorModuleImp, int i)
    {
        ViewDataBinding binding = holder.getViewDataBinding();
        binding.setVariable(BR.data, monitorModuleImp);
        binding.setVariable(BR.itemClickListener, this);
        binding.executePendingBindings();
    }
}