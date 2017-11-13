package com.android.devin.core.ui.widget.recyclerview;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:33
 */
public abstract class DataBindingAdapter<T> extends Adapter<DataBindingHolder<T>> implements DataBindingHandler<T>
{
    private final LayoutInflater mLayoutInflater;

    private OnItemClickListener<T> mOnItemClickListener;

    protected List<T> mDataList;

    public DataBindingAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        mLayoutInflater = layoutInflater;

        mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount()
    {
        return mDataList != null ? mDataList.size() : 0;
    }

    // create viewHolder
    public abstract DataBindingHolder<T> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public final DataBindingHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    // bind viewHolder
    protected abstract int[] getVariableIds();

    @Override
    public void onBindViewHolder(DataBindingHolder<T> holder, int position)
    {
        holder.onViewDataBinding(getVariableIds(), getItem(position));
    }

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

    // --------------------------------------------------------------------------------------
    // data
    // --------------------------------------------------------------------------------------
    public void setData(List<T> list)
    {
        mDataList = list;

        notifyDataSetChanged();
    }

    protected final void updateData(List<T> list)
    {
        mDataList = list;
    }

    public void appendData(T t, int pos)
    {
        if (mDataList != null)
        {
            mDataList.add(pos, t);
        }
        else
        {
            mDataList = new ArrayList<>();

            mDataList.add(t);
        }

        notifyDataSetChanged();
    }

    public void appendData(List<T> list)
    {
        if (mDataList != null)
        {
            mDataList.addAll(list);
        }
        else
        {
            mDataList = list;
        }

        notifyDataSetChanged();
    }

    public int findItemPosition(T t)
    {
        if (mDataList != null && t != null)
        {
            for (int i = 0; i < mDataList.size(); i++)
            {
                if (t.equals(mDataList.get(i)))
                {
                    return i;
                }
            }
        }

        return -1;
    }

    public T getItem(int position)
    {
        if (position < 0 || position >= getItemCount())
        {
            return null;
        }

        return mDataList != null ? mDataList.get(position) : null;
    }

    public List<T> getDataList()
    {
        return mDataList;
    }

    @SuppressWarnings("unused")
    protected LayoutInflater getLayoutInflater()
    {
        return mLayoutInflater;
    }

}
