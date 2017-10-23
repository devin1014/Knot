package com.android.smartlink.ui.widget;

import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter;
import com.android.devin.core.ui.widget.recyclerview.DataBindingAdapter.OnItemClickListener;
import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.widget.adapter.FilterAdapter;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-23
 * Time: 17:02
 */
public class FilterPopupWindow extends PopupWindow implements OnItemClickListener<UIFilter>
{
    private OnCheckChangedListener mOnCheckChangedListener;

    private FilterAdapter mFilterAdapter;

    public FilterPopupWindow(Activity activity, OnCheckChangedListener listener)
    {
        mOnCheckChangedListener = listener;

        setWidth(LayoutParams.MATCH_PARENT);

        setHeight(LayoutParams.WRAP_CONTENT);

        setTouchable(true);

        setOutsideTouchable(true);

        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.filter_window_bg));

        RecyclerView recyclerView = new RecyclerView(activity);

        recyclerView.setFadingEdgeLength(0);

        recyclerView.setVerticalFadingEdgeEnabled(false);

        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        DividerItemDecoration decoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);

        decoration.setDrawable(activity.getResources().getDrawable(R.drawable.filter_divider));

        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(mFilterAdapter = new FilterAdapter(activity.getLayoutInflater(), this));

        setContentView(recyclerView);
    }

    @Override
    public void onItemClick(DataBindingAdapter<UIFilter> adapter, View view, UIFilter module, int position)
    {
        view.setSelected(!view.isSelected());

        if (mOnCheckChangedListener != null)
        {
            mOnCheckChangedListener.onItemCheckChanged(module, view.isSelected());
        }
    }

    public interface OnCheckChangedListener
    {
        void onItemCheckChanged(UIFilter module, boolean checked);
    }

    public void setFilterList(List<UIFilter> list)
    {
        mFilterAdapter.setData(list);
    }
}
