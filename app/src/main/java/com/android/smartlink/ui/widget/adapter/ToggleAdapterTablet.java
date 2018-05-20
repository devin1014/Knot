package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIToggle;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-18
 * Time: 15:30
 */
public class ToggleAdapterTablet extends BaseAdapter<UIToggle>
{
    public ToggleAdapterTablet(LayoutInflater layoutInflater, OnItemClickListener<UIToggle> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.adapter_item_toggle;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIToggle> holder, int position)
    {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void onItemClick(DataBindingAdapter<UIToggle> adapter, View view, UIToggle uiToggle, int position)
    {
        super.onItemClick(adapter, view, uiToggle, position);
    }
}
