package com.android.devin.core.ui.widget.recyclerview;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.util.ListUpdateCallback;
import android.view.LayoutInflater;

import com.android.devin.core.util.LogUtil;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-13
 * Time: 10:16
 */
public abstract class DiffDataBindingAdapter<T extends IDiffCompare<T>> extends DataBindingAdapter<T> implements ListUpdateCallback
{
    private DiffCompare<T> mDiffCompare;

    public DiffDataBindingAdapter(LayoutInflater layoutInflater, OnItemClickListener<T> listener)
    {
        super(layoutInflater, listener);

        mDiffCompare = new DiffCompare<>();
    }

    @Override
    public void setData(List<T> list)
    {
        if (getDataList() == null || list == null)
        {
            super.setData(list);
        }
        else
        {
            final List<T> oldList = getDataList();

            updateData(list);

            if (list == oldList)
            {
                LogUtil.warn(this, "old list and new list is same object!");
            }

            LogUtil.set();

            DiffResult diffResult = DiffUtil.calculateDiff(mDiffCompare.setData(oldList, list), false);

            LogUtil.test(this, "calculateDiff:" + oldList.size() + "," + list.size());

            diffResult.dispatchUpdatesTo(mListUpdateCallbackWrapper);
        }
    }

    @Override
    public void appendData(T t, int pos)
    {
        if (mDataList != null)
        {
            mDataList.add(pos, t);

            onInserted(pos, 1);
        }
        else
        {
            super.appendData(t, pos);
        }
    }

    @Override
    public void appendData(List<T> list)
    {
        if (mDataList != null)
        {
            mDataList.addAll(list);

            onInserted(mDataList.size(), list.size());
        }
        else
        {
            super.appendData(list);
        }
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<T> holder, int position, List<Object> payloads)
    {
        LogUtil.info(this, "onBindViewHolder:" + holder + "," + position + "," + payloads);

        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onInserted(int position, int count)
    {
        LogUtil.log(this, "onInserted:" + position + "," + count);

        notifyItemRangeInserted(position, count);
    }

    @Override
    public void onRemoved(int position, int count)
    {
        LogUtil.log(this, "onRemoved:" + position + "," + count);

        notifyItemRangeRemoved(position, count);
    }

    @Override
    public void onMoved(int fromPosition, int toPosition)
    {
        LogUtil.log(this, "onMoved:" + fromPosition + "," + toPosition);

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onChanged(int position, int count, Object payload)
    {
        LogUtil.log(this, "onChanged:" + position + "," + count + "," + payload);

        notifyItemRangeChanged(position, count, payload);
    }

    private class DiffCompare<V extends IDiffCompare<V>> extends DiffUtil.Callback
    {
        private List<V> mOldData;

        private List<V> mNewData;

        public DiffCompare setData(List<V> oldList, List<V> newList)
        {
            mOldData = oldList;

            mNewData = newList;

            return this;
        }

        @Override
        public int getOldListSize()
        {
            int size = mOldData != null ? mOldData.size() : 0;

            LogUtil.info(this, "getOldListSize:" + size);

            return size;
        }

        @Override
        public int getNewListSize()
        {
            int size = mNewData != null ? mNewData.size() : 0;

            LogUtil.info(this, "getNewListSize:" + size);

            return size;
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
        {
            LogUtil.info(this, "areItemsTheSame:[oldItemPosition=" + oldItemPosition + ",newItemPosition=" + newItemPosition + "]");

            return mOldData.get(oldItemPosition).compareObject(mNewData.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
        {
            LogUtil.info(this, "areContentsTheSame:[oldItemPosition=" + oldItemPosition + ",newItemPosition=" + newItemPosition + "]");

            return mOldData.get(oldItemPosition).compareContent(mNewData.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition)
        {
            LogUtil.info(this, "getChangePayload:[oldItemPosition=" + oldItemPosition + ",newItemPosition=" + newItemPosition + "]");

            Object object = mOldData.get(oldItemPosition).getChangePayload();

            if (object == null)
            {
                object = mNewData.get(newItemPosition).getChangePayload();
            }

            return object;
        }
    }

    private ListUpdateCallbackWrapper mListUpdateCallbackWrapper = new ListUpdateCallbackWrapper();

    private class ListUpdateCallbackWrapper implements ListUpdateCallback
    {
        @Override
        public void onInserted(int position, int count)
        {
            DiffDataBindingAdapter.this.onInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count)
        {
            DiffDataBindingAdapter.this.onRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition)
        {
            DiffDataBindingAdapter.this.onMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload)
        {
            DiffDataBindingAdapter.this.onChanged(position, count, payload);
        }
    }
}
