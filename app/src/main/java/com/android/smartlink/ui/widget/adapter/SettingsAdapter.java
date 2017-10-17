package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.devin.core.ui.widget.recyclerview.DataBindingHolder;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UISetting;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:42
 */
public class SettingsAdapter extends BaseAdapter<UISetting>
{
    private DataBindingHandler<UISetting> mBindingHandler;

    public SettingsAdapter(LayoutInflater layoutInflater, DataBindingHandler<UISetting> handler)
    {
        super(layoutInflater);

        mBindingHandler = handler;
    }

    @Override
    public DataBindingHolder<UISetting> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType)
    {
        return new DataBindingHolder<>(inflater, parent, R.layout.list_item_settings, mBindingHandler);
    }
}
