package com.android.smartlink.ui.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIModule;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;

/**
 * User: LIUWEI
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
    public void onBindViewHolder(DataBindingHolder<UIModule> holder, int position)
    {
        super.onBindViewHolder(holder, position);

        if (getItem(position).isError())
        {
            //AnimationUtil.scaling(holder.itemView);
        }
        else
        {
            //AnimationUtil.stopAnimation(holder.itemView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        // should stop all animation when destroy recycler view.
    }
}