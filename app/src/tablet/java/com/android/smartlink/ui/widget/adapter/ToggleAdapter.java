package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.databinding.AdapterToggleBindingImpl;
import com.android.smartlink.ui.model.UIToggleModule;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: liuwei
 * Date: 2018-05-18
 * Time: 15:30
 */
public class ToggleAdapter extends DiffDataBindingAdapter<UIToggleModule>
{
    public ToggleAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIToggleModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.adapter_toggle;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIToggleModule> holder, UIToggleModule toggleModule, int position)
    {
        //super function do not work,because object is interface!
        //super.onBindViewHolder(holder, toggleModule, position);
        ((AdapterToggleBindingImpl) holder.getViewDataBinding()).setData(toggleModule);
        ((AdapterToggleBindingImpl) holder.getViewDataBinding()).setItemClickListener(this);
        holder.getViewDataBinding().executePendingBindings();
    }
}