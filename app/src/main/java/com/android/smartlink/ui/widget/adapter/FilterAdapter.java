package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIFilter;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 17:13
 */
public class FilterAdapter extends BaseAdapter<UIFilter>
{
    public FilterAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIFilter> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<UIFilter> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new DataBindingHolder<>(inflater, parent, R.layout.list_item_filter, this);
    }
}
