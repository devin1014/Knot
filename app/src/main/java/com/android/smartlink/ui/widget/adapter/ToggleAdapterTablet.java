package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.ToggleModuleImp;

import java.util.List;

/**
 * User: liuwei
 * Date: 2018-05-18
 * Time: 15:30
 */
public class ToggleAdapterTablet extends BaseAdapter<ToggleModuleImp>
{
    public ToggleAdapterTablet(LayoutInflater layoutInflater, OnItemClickListener<ToggleModuleImp> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.adapter_item_toggle;
    }

    @Override
    protected void onDataSetChanged(List<ToggleModuleImp> oldList, List<ToggleModuleImp> newList)
    {
        notifyDataSetChanged(); //todo,should fix! toggle status
    }
}
