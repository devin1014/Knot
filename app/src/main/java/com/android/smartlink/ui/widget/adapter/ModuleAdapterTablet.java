package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;

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
        return R.layout.item_home_module;
    }

    @Override
    protected void onItemClick(DataBindingAdapter<UIModule> adapter, View view, UIModule uiModule, int position)
    {
        super.onItemClick(adapter, view, uiModule, position);
    }
}