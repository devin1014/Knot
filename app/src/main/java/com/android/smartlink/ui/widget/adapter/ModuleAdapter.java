package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends BaseAdapter<UIModule>
{
    public ModuleAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int type)
    {
        if (type == 0) // first item always main module
        {
            return R.layout.item_home_main_module;
        }

        return R.layout.item_home_module;
    }

    @Override
    protected void onItemClick(DataBindingAdapter<UIModule> adapter, View view, UIModule uiModule, int position)
    {
        super.onItemClick(adapter, view, uiModule, position);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

}
