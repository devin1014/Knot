package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIEvent;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:00
 */
public class EventsAdapter extends BaseAdapter<UIEvent>
{
    private DataBindingHandler<UIEvent> mDataBindingHandler;

    public EventsAdapter(LayoutInflater layoutInflater, DataBindingHandler<UIEvent> handler)
    {
        super(layoutInflater);

        mDataBindingHandler = handler;
    }

    @Override
    public DataBindingHolder<UIEvent> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new DataBindingHolder<>(inflater.inflate(R.layout.list_item_events, parent, false), mDataBindingHandler);
    }
}
