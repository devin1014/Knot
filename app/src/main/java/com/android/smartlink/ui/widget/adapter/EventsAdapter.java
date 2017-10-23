package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:00
 */
public class EventsAdapter extends BaseAdapter<UIEvent>
{
    private List<UIEvent> mAllList;

    private List<UIFilter> mFilters;

    public EventsAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIEvent> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    public DataBindingHolder<UIEvent> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new DataBindingHolder<>(inflater.inflate(R.layout.list_item_events, parent, false), this);
    }

    @Override
    public void setData(List<UIEvent> list)
    {
        super.setData(list);

        mAllList = list;

        if (mFilters != null)
        {
            setFilter(mFilters);
        }
    }

    public void setFilter(List<UIFilter> filters)
    {
        mFilters = filters;

        if (mAllList != null)
        {
            List<UIEvent> result = new ArrayList<>(mAllList.size());

            for (UIEvent event : mAllList)
            {
                // event id =0, add to result
                if (event.getId() == Constants.ID_ALL)
                {
                    result.add(event);

                    continue;
                }

                for (UIFilter filter : filters)
                {
                    if (event.getId() == filter.getId())
                    {
                        if (filter.isChecked())
                        {
                            result.add(event);

                            break;
                        }
                    }
                }
            }

            // call super!!! mAllList still contains all data.
            super.setData(result);
        }
    }
}
