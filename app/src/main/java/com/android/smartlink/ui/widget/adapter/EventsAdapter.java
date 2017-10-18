package com.android.smartlink.ui.widget.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.widget.adapter.EventsAdapter.HeaderHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:00
 */
public class EventsAdapter extends BaseAdapter<UIEvent> implements StickyRecyclerHeadersAdapter<HeaderHolder>
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

    @Override
    public long getHeaderId(int position)
    {
        return getItem(position).getDay();
    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent)
    {
        return new HeaderHolder(getLayoutInflater().inflate(R.layout.list_item_events_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(HeaderHolder holder, int position)
    {
        holder.setData(getItem(position));
    }

    class HeaderHolder extends ViewHolder
    {
        TextView title;

        HeaderHolder(View itemView)
        {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void setData(UIEvent event)
        {
            title.setText(event.getDate());
        }
    }
}
