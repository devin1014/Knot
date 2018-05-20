package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-25
 * Time: 15:54
 */
public class ModuleAdapterTablet extends BaseAdapter<UIModule>
{
    public ModuleAdapterTablet(LayoutInflater layoutInflater)
    {
        super(layoutInflater, null);
    }

    @Override
    protected int getLayout(int type)
    {
        if (type == 0)
        {
            return R.layout.item_home_main_module;
        }

        return R.layout.item_home_module;
    }

    @Override
    public int getViewType(int position)
    {
        return position;
    }
}