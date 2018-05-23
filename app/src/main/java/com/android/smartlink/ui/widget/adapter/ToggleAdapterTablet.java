package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIToggle;

import java.util.List;

/**
 * User: liuwei(wei.liu@neulion.com.com)
 * Date: 2018-05-18
 * Time: 15:30
 */
public class ToggleAdapterTablet extends BaseAdapter<UIToggle>
{
    public ToggleAdapterTablet(LayoutInflater layoutInflater, OnItemClickListener<UIToggle> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.adapter_item_toggle;
    }

    @Override
    protected void onDataSetChanged(List<UIToggle> oldList, List<UIToggle> newList)
    {
        notifyDataSetChanged(); //todo,should fix! toggle status
    }
}
