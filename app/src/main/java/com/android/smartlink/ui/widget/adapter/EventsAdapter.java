package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIEvent;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:00
 */
public class EventsAdapter extends BaseAdapter<UIEvent>
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
}
