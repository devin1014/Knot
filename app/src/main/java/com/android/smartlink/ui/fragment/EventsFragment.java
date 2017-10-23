package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.devin.core.ui.widget.recyclerview.CommonItemDecoration;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.EventsRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Events;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.widget.FilterPopupWindow;
import com.android.smartlink.ui.widget.FilterPopupWindow.OnCheckChangedListener;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.EventsAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.FileUtil;

import java.util.List;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 18:00
 */
public class EventsFragment extends BaseSmartlinkFragment implements RequestCallback<Events>, OnRefreshListener, OnCheckChangedListener
{
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.events_filter_anchor)
    View mFilterAnchorView;

    private EventsRequestProvider mRequestProvider;

    private EventsAdapter mEventsAdapter;

    private FilterPopupWindow mFilterPopupWindow;

    private List<UIFilter> mFilters;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.events_list_divider)));

        mRecyclerView.setAdapter(mEventsAdapter = new EventsAdapter(getActivity().getLayoutInflater(), null));

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRequestProvider = new EventsRequestProvider(this);

        mRequestProvider.request("http://localhost:8080/examples/smartlink/events.json");

        mLoadingLayout.showLoading();

        mFilters = ConvertUtil.convertFilters(AppManager.getInstance().getModules());

        mEventsAdapter.setFilter(mFilters);
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Events events)
    {
        mLoadingLayout.showContent();

        mSwipeRefreshLayout.setRefreshing(false);

        mEventsAdapter.setData(ConvertUtil.convertEvents(events.getEvents()));
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));

        mSwipeRefreshLayout.setRefreshing(false);

        //FIXME,can not get feed from server ,read local file
        {
            Events events = FileUtil.openAssets(getActivity(), "events.json", Events.class);

            if (events != null)
            {
                mEventsAdapter.setData(ConvertUtil.convertEvents(events.getEvents()));

                mLoadingLayout.showContent();

                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onRefresh()
    {
        // call request
        mRequestProvider.request("http://localhost:8080/examples/smartlink/events.json");

        // hide loading and show blank loading view
        mLoadingLayout.showBlankView();
    }

    @Override
    public void onEditClick(boolean selected)
    {
        if (mFilterPopupWindow == null)
        {
            mFilterPopupWindow = new FilterPopupWindow(getActivity(), this);
        }

        mFilterPopupWindow.setFilterList(mFilters);

        mFilterPopupWindow.showAsDropDown(mFilterAnchorView);
    }

    @Override
    public void onItemCheckChanged(UIFilter module, boolean checked)
    {
        for (UIFilter filter : mFilters)
        {
            if (filter.getId() == module.getId())
            {
                filter.setChecked(checked);

                break;
            }
        }

        mEventsAdapter.setFilter(mFilters);
    }
}
