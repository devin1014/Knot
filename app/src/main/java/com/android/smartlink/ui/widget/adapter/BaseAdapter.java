package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.BR;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:24
 */
abstract class BaseAdapter<T> extends DataBindingAdapter<T>
{
    BaseAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected final int[] getVariableIds()
    {
        return new int[]{BR.data, BR.handler};
    }
}
