package com.android.smartlink.ui.widget;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

import com.android.smartlink.R;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.widget.adapter.FilterAdapter;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter.OnItemClickListener;

import java.util.List;

/**
 * User: LIUWEI
 * Date: 2017-10-23
 * Time: 17:02
 */
public class FilterPopupWindow extends PopupWindow implements OnItemClickListener<UIFilter>
{
    private OnFilterChangedListener mOnFilterChangedListener;

    private FilterAdapter mFilterAdapter;

    public FilterPopupWindow(Activity activity, OnFilterChangedListener listener)
    {
        mOnFilterChangedListener = listener;

        setWidth(LayoutParams.MATCH_PARENT);

        setHeight(LayoutParams.WRAP_CONTENT);

        setTouchable(true);

        setOutsideTouchable(true);

        //noinspection deprecation
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.filter_window_bg));

        RecyclerView recyclerView = new RecyclerView(activity);

        recyclerView.setFadingEdgeLength(0);

        recyclerView.setVerticalFadingEdgeEnabled(false);

        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        DividerItemDecoration decoration = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);

        //noinspection deprecation
        decoration.setDrawable(activity.getResources().getDrawable(R.drawable.filter_divider));

        recyclerView.addItemDecoration(decoration);

        recyclerView.setAdapter(mFilterAdapter = new FilterAdapter(activity.getLayoutInflater(), this));

        setContentView(recyclerView);
    }

    @Override
    public void onItemClick(DataBindingAdapter<UIFilter> adapter, View view, UIFilter module, int position)
    {
        view.setSelected(!view.isSelected());

        module.setChecked(view.isSelected());

        if (mOnFilterChangedListener != null)
        {
            mOnFilterChangedListener.onFilterChanged(mFilterAdapter.getDataList());
        }
    }

    public interface OnFilterChangedListener
    {
        void onFilterChanged(@NonNull List<UIFilter> filters);
    }

    public void setFilterList(List<UIFilter> list)
    {
        mFilterAdapter.setData(list);
    }
}
