package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.EventsRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Events;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIFilter;
import com.android.smartlink.ui.widget.FilterPopupWindow;
import com.android.smartlink.ui.widget.FilterPopupWindow.OnFilterChangedListener;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.EventsAdapter;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.Utils;
import com.neulion.core.widget.recyclerview.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-16
 * Time: 18:00
 */
public class EventsFragment extends BaseSmartlinkFragment implements RequestCallback<Events>, OnFilterChangedListener
{
    public static EventsFragment newInstance(int... ids)
    {
        EventsFragment fragment = new EventsFragment();

        Bundle arguments = new Bundle();

        arguments.putIntArray(Constants.KEY_EXTRA_IDS, ids);

        fragment.setArguments(arguments);

        return fragment;
    }

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

    private List<UIEvent> mUIEvents;

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
        int[] ids = getArguments().getIntArray(Constants.KEY_EXTRA_IDS);

        mFilters = ConvertUtil.convertFilters(AppManager.getInstance().getAllModules(), ids, Utils.isEmpty(ids));

        mRecyclerView.setAdapter(mEventsAdapter = new EventsAdapter(getActivity().getLayoutInflater()));

        mRequestProvider = EventsRequestProvider.newInstance(this);

        //mRequestProvider.schedule(RequestUrl.obtainEventsUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);
        mRequestProvider.request(RequestUrl.obtainEventsUrl());

        mLoadingLayout.showLoading();
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

        setDataByFilters(mUIEvents = ConvertUtil.convertEvents(events.getEvents()), mFilters);
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));
    }

    @Override
    public void onEditClick(boolean selected)
    {
        if (mFilterPopupWindow == null)
        {
            mFilterPopupWindow = new FilterPopupWindow(getActivity(), this);

            mFilterPopupWindow.setFilterList(mFilters);
        }

        mFilterPopupWindow.showAsDropDown(mFilterAnchorView);
    }

    @Override
    public void onFilterChanged(@NonNull List<UIFilter> filters)
    {
        mFilters = filters;

        setDataByFilters(mUIEvents, mFilters);
    }

    private void setDataByFilters(List<UIEvent> list, List<UIFilter> filters)
    {
        if (list == null || list.size() == 0 || filters == null || filters.size() == 0)
        {
            mLoadingLayout.showMessage(getString(R.string.empty_events));

            return;
        }

        List<UIEvent> result = new ArrayList<>(list.size());

        for (UIEvent event : list)
        {
            for (UIFilter filter : filters)
            {
                if (event.getId() == filter.getId())
                {
                    if (filter.isChecked())
                    {
                        result.add(event);
                    }

                    break;
                }
            }
        }

        if (result.size() == 0)
        {
            mLoadingLayout.showMessage(getString(R.string.empty_events));
        }
        else
        {
            mLoadingLayout.showContent();

            Collections.sort(result);

            mEventsAdapter.setData(result);
        }
    }

}
