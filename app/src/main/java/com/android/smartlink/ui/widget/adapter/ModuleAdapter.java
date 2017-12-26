package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;

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
    public int getItemViewType(int position)
    {
        return position;
    }

}
