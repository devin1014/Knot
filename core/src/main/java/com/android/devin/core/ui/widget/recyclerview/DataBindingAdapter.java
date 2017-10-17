package com.android.devin.core.ui.widget.recyclerview;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-05-18
 * Time: 16:33
 */
public abstract class DataBindingAdapter<T> extends Adapter<DataBindingHolder<T>>
{
    private final LayoutInflater mLayoutInflater;

    private List<T> mDataList;

    public DataBindingAdapter(LayoutInflater layoutInflater)
    {
        mLayoutInflater = layoutInflater;
    }

    public void setData(List<T> list)
    {
        mDataList = list;

        notifyDataSetChanged();
    }

    public void setData(T[] array)
    {
        // clear list data first!
        mDataList = Arrays.asList(array);

        notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
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

    @Override
    public final DataBindingHolder<T> onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return onCreateViewHolder(mLayoutInflater, parent, viewType);
    }

    public abstract DataBindingHolder<T> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected abstract int[] getVariableIds();

    @Override
    public void onBindViewHolder(DataBindingHolder<T> holder, int position)
    {
        holder.onViewDataBinding(getVariableIds(), getItem(position));
    }

    @Override
    public int getItemCount()
    {
        return mDataList != null ? mDataList.size() : 0;
    }

    @SuppressWarnings("WeakerAccess")
    public T getItem(int position)
    {
        if (position < 0 || position >= getItemCount())
        {
            return null;
        }

        if (mDataList != null)
        {
            return mDataList.get(position);
        }

        return null;
    }

    public List<T> getDataList()
    {
        return mDataList;
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    protected LayoutInflater getLayoutInflater()
    {
        return mLayoutInflater;
    }
}
