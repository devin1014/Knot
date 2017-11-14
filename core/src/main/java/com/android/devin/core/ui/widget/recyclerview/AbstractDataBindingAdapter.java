package com.android.devin.core.ui.widget.recyclerview;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-14
 * Time: 16:23
 */
abstract class AbstractDataBindingAdapter<T> extends Adapter<DataBindingHolder<T>>
{
    protected LayoutInflater mLayoutInflater;

    protected List<T> mDataList;

    public AbstractDataBindingAdapter(LayoutInflater inflater)
    {
        mLayoutInflater = inflater;
    }

    @Override
    public int getItemCount()
    {
        return mDataList != null ? mDataList.size() : 0;
    }

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

}
