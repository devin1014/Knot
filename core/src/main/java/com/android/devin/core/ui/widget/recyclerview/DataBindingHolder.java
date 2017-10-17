package com.android.devin.core.ui.widget.recyclerview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:34
 */
public class DataBindingHolder<T> extends ViewHolder
{
    protected final ViewDataBinding mViewDataBinding;

    protected DataBindingHandler<T> mDataBindingHandler;

    public DataBindingHolder(View itemView, DataBindingHandler<T> handler)
    {
        super(itemView);

        mDataBindingHandler = handler;

        mViewDataBinding = DataBindingUtil.bind(itemView);
    }

    public DataBindingHolder(LayoutInflater inflater, ViewGroup parent, int layoutId, DataBindingHandler<T> handler)
    {
        this(inflater.inflate(layoutId, parent, false), handler);
    }

    public void onViewDataBinding(int[] variableId, T t)
    {
        itemView.setTag(t);

        mViewDataBinding.setVariable(variableId[0], t);

        mViewDataBinding.setVariable(variableId[1], mDataBindingHandler);

        mViewDataBinding.executePendingBindings();
    }

    @SuppressWarnings("unchecked")
    protected <V extends View> V findViewById(int id)
    {
        View view = itemView.findViewById(id);

        if (view != null)
        {
            return (V) view;
        }

        return null;
    }
}
