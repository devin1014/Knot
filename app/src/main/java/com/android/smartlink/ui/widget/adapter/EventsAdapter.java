package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.databinding.ListItemEventsBinding;
import com.android.smartlink.ui.model.UIEvent;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 16:00
 */
public class EventsAdapter extends DiffDataBindingAdapter<UIEvent>
{
    public EventsAdapter(LayoutInflater layoutInflater)
    {
        super(layoutInflater, null);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_item_events;
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UIEvent> holder, UIEvent uiEvent, int i)
    {
        ListItemEventsBinding binding = holder.getViewDataBinding();
        binding.setData(uiEvent);
        binding.executePendingBindings();
    }
}
