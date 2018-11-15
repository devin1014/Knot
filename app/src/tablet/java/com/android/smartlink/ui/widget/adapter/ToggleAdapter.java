package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.ToggleModuleImp;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: liuwei
 * Date: 2018-05-18
 * Time: 15:30
 */
public class ToggleAdapter extends DiffDataBindingAdapter<ToggleModuleImp>
{
    public ToggleAdapter(LayoutInflater layoutInflater, OnItemClickListener<ToggleModuleImp> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.adapter_toggle;
    }
}