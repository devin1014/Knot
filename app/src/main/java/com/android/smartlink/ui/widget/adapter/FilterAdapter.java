package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIFilter;

/**
 * User: LIUWEI
 * Date: 2017-10-23
 * Time: 17:13
 */
public class FilterAdapter extends BaseAdapter<UIFilter>
{
    public FilterAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIFilter> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_item_filter;
    }
}
