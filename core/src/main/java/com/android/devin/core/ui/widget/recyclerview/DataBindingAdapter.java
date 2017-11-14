package com.android.devin.core.ui.widget.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:33
 */
public abstract class DataBindingAdapter<T> extends AbstractDataBindingAdapter<T> implements DataBindingHandler<T>
{
    private OnItemClickListener<T> mOnItemClickListener;

    public DataBindingAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        super(layoutInflater);

        mOnItemClickListener = listener;
    }

    @Override
    public final DataBindingHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    // create viewHolder
    public abstract DataBindingHolder<T> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(DataBindingHolder<T> holder, int position)
    {
        holder.onViewDataBinding(getVariableIds(), getItem(position));
    }

    // bind viewHolder
    protected abstract int[] getVariableIds();

    // on click listener
    public interface OnItemClickListener<T>
    {
        void onItemClick(DataBindingAdapter<T> adapter, View view, T t, int position);
    }

    @Override
    public final void onItemClick(View view, T t)
    {
        onItemClick(this, view, t, findItemPosition(t));
    }

    @SuppressWarnings("UnusedParameters")
    protected void onItemClick(DataBindingAdapter<T> adapter, View view, T t, int position)
    {
        if (mOnItemClickListener != null)
        {
            mOnItemClickListener.onItemClick(adapter, view, t, position);
        }
    }
}
