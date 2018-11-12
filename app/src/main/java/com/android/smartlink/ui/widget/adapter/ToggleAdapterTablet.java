package com.android.smartlink.ui.widget.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;

import com.android.smartlink.BR;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.ToggleModuleImp;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import java.util.List;

/**
 * User: liuwei
 * Date: 2018-05-18
 * Time: 15:30
 */
public class ToggleAdapterTablet extends DiffDataBindingAdapter<ToggleModuleImp>
{
    public ToggleAdapterTablet(LayoutInflater layoutInflater, OnItemClickListener<ToggleModuleImp> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.adapter_item_toggle;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<ToggleModuleImp> holder, ToggleModuleImp toggleModuleImp, int i)
    {
        ViewDataBinding binding = holder.getViewDataBinding();
        binding.setVariable(BR.data, toggleModuleImp);
        binding.setVariable(BR.itemClickListener, this);
        binding.executePendingBindings();
    }

    @Override
    protected void onDataSetChanged(List<ToggleModuleImp> oldList, List<ToggleModuleImp> newList)
    {
        notifyDataSetChanged(); //todo,should fix! toggle status
    }
}
