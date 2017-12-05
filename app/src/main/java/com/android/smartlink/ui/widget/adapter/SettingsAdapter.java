package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UISetting;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-17
 * Time: 16:42
 */
public class SettingsAdapter extends BaseAdapter<UISetting>
{
    public SettingsAdapter(LayoutInflater layoutInflater, OnItemClickListener<UISetting> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_item_settings;
    }
}
