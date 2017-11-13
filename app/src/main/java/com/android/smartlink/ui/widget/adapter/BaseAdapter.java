package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.devin.core.ui.widget.recyclerview.DiffDataBindingAdapter;
import com.android.devin.core.ui.widget.recyclerview.IDiffCompare;
import com.android.smartlink.BR;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:24
 */
abstract class BaseAdapter<T extends IDiffCompare<T>> extends DiffDataBindingAdapter<T>
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
