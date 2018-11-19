package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.neulion.core.widget.recyclerview.adapter.DiffDataBindingAdapter;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: LIUWEI
 * Date: 2017-10-18
 * Time: 15:56
 */
public class ModuleAdapter extends DiffDataBindingAdapter<UIMonitorModule>
{
    public ModuleAdapter(LayoutInflater layoutInflater, OnItemClickListener<UIMonitorModule> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int type)
    {
        if (type == 0) // first item always main module
        {
            return R.layout.adapter_module_main;
        }

        return R.layout.adapter_module;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    // ----------------------------------------------------------------
    // HeadHolder
    // ----------------------------------------------------------------
    //    public static class HeadHolder extends DataBindingHolder<UIMonitorModule>
    //    {
    //        private ModuleStatusLayout mModuleStatusLayout;
    //
    //        HeadHolder(View itemView, OnItemClickListener<UIMonitorModule> handler)
    //        {
    //            super(itemView, handler);
    //
    //            mModuleStatusLayout = (ModuleStatusLayout) itemView;
    //        }
    //
    //        //        @SuppressWarnings("unchecked")
    //        //        @Override
    //        //        public void onBindViewHolder(Object object)
    //        //        {
    //        //            mModuleStatusLayout.setModules((List<MonitorModuleData>) object);
    //        //        }
    //
    //        public ModuleStatusLayout getModuleStatusLayout()
    //        {
    //            return mModuleStatusLayout;
    //        }
    //    }
}
