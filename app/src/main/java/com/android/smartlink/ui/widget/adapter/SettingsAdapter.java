package com.android.smartlink.ui.widget.adapter;

import android.view.LayoutInflater;
import android.view.View;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UISetting;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.holder.DataBindingHolder;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

/**
 * User: LIUWEI
 * Date: 2017-10-17
 * Time: 16:42
 */
public class SettingsAdapter extends DataBindingAdapter<UISetting> implements OnItemClickListener<UISetting>
{
    private int mSelectedPosition = 0;

    public SettingsAdapter(LayoutInflater layoutInflater, OnItemClickListener<UISetting> listener)
    {
        super(layoutInflater, listener);
    }

    @Override
    protected int getLayout(int i)
    {
        return R.layout.list_item_settings;
    }

    private static final int POS_FEEDBACK = 2; // not selected 'FeedBack' menu

    @Override
    public void onItemClick(View view, UISetting uiSetting)
    {
        super.onItemClick(view, uiSetting);

        final int position = findItemPosition(uiSetting);

        final int lastPosition = mSelectedPosition;

        if (position != POS_FEEDBACK && lastPosition != position)
        {
            mSelectedPosition = position;

            notifyItemChanged(position);

            notifyItemChanged(lastPosition);
        }
    }

    @Override
    public void onBindViewHolder(DataBindingHolder<UISetting> holder, UISetting uiSetting, int i)
    {
        super.onBindViewHolder(holder, uiSetting, i);

        holder.itemView.setSelected(mSelectedPosition == i);
    }
}
