package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends BaseAdapter<UIModule>
{
    private DataBindingHandler<UIModule> mDataBindingHandler;

    public ModuleAdapter(LayoutInflater layoutInflater, DataBindingHandler<UIModule> handler)
    {
        super(layoutInflater);

        mDataBindingHandler = handler;
    }

    @Override
    public DataBindingHolder<UIModule> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new DataBindingHolder<>(inflater, parent, R.layout.list_item_module, mDataBindingHandler);
    }
}
