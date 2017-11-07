package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.EventsRequestProvider;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Events;
import com.android.smartlink.bean.Events.Event;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.DetailActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIEvent;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.DataBindingAdapterUtil;
import com.android.smartlink.util.HttpUrl;
import com.android.smartlink.util.UICompat;

import java.util.List;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-27
 * Time: 10:18
 */
public class NestScrollHomeFragment extends BaseSmartlinkFragment implements RequestCallback<Modules>, OnRefreshListener
{
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.content_container)
    LinearLayout mContentContainer;

    @BindView(R.id.weather_root)
    View mWeatherRoot;

    private ViewDataBinding mAlarmViewDataBinding;

    private MainRequestProvider mRequestProvider;

    private EventsRequestProvider mEventsRequestProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_nestscroll_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mEventsRequestProvider = new EventsRequestProvider(getActivity(), mEventsRequestCallback);

        mRequestProvider = new MainRequestProvider(getActivity(), this);

        mRequestProvider.request(HttpUrl.getHomeUrl());

        mLoadingLayout.showLoading();

        Weather weather = AppManager.getInstance().getWeather();

        mWeatherRoot.setVisibility(weather != null ? View.VISIBLE : View.GONE);

        if (weather != null)
        {
            ViewDataBinding viewDataBinding = DataBindingUtil.bind(mWeatherRoot);

            viewDataBinding.setVariable(BR.data, new UIWeather(weather));

            viewDataBinding.executePendingBindings();
        }
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        mLoadingLayout.showContent();

        mSwipeRefreshLayout.setRefreshing(false);

        AppManager.getInstance().setModules(modules.getModules());

        resetModulesView(modules.getModules());
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        // call request
        mRequestProvider.request(HttpUrl.getHomeUrl());

        // hide loading and show blank loading view
        mLoadingLayout.showBlankView();
    }

    private DataBindingHandler<UIModule> mDataBindingHandler = new DataBindingHandler<UIModule>()
    {
        @Override
        public void onItemClick(View view, UIModule uiModule)
        {
            DetailActivity.startActivity(getActivity(), uiModule.getName(), uiModule);
        }
    };

    private void resetModulesView(List<Module> modules)
    {
        mContentContainer.removeAllViews();

        // status
        addStatus(modules);

        int index = 0;

        // main module
        {
            View view = getActivity().getLayoutInflater().inflate(R.layout.comp_home_main_module, mContentContainer, false);

            mContentContainer.addView(view);

            ViewDataBinding mainViewDataBinding = DataBindingUtil.bind(view);

            mainViewDataBinding.setVariable(BR.data, new UIModule(modules.get(index)));

            mainViewDataBinding.setVariable(BR.handler, mDataBindingHandler);

            mainViewDataBinding.executePendingBindings();

            index++;
        }

        int size = modules.size() - 1;

        while (size >= 6 || (size >= 3 && size % 3 != 1))
        {
            addModules(modules, index, 3);

            index = index + 3;

            size = size - 3;
        }

        while (size != 0 && size % 2 == 0)
        {
            addModules(modules, index, 2);

            index = index + 2;

            size = size - 2;
        }

        if (size == 1)
        {
            addModules(modules, index, 1);
        }
    }

    private void addStatus(List<Module> modules)
    {
        Module alarmModule = modules.get(0);

        for (Module module : modules)
        {
            if (module.getStatus() == Constants.STATUS_ERROR)
            {
                alarmModule = module;

                break;
            }

            if (UIModule.getStatus(module) > UIModule.getStatus(alarmModule))
            {
                alarmModule = module;
            }
        }

        boolean alarm = UIModule.getStatus(alarmModule) != Constants.STATUS_NORMAL;

        LayoutInflater inflater = getActivity().getLayoutInflater();

        ViewGroup statusRootView = (ViewGroup) inflater.inflate(R.layout.list_item_home_status, mContentContainer, false);

        mContentContainer.addView(statusRootView);

        final TextView statusName = (TextView) statusRootView.findViewById(R.id.status_title);

        final ImageView statusImg = (ImageView) statusRootView.findViewById(R.id.status_image);

        final ImageView arrowImg = (ImageView) statusRootView.findViewById(R.id.arrow_image);

        final ViewGroup statusGroup = (ViewGroup) statusRootView.findViewById(R.id.status_details);

        arrowImg.setSelected(alarm); // alarm show status content

        statusGroup.setVisibility(alarm ? View.VISIBLE : View.GONE);

        if (!alarm)
        {
            statusRootView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    arrowImg.setSelected(!arrowImg.isSelected());

                    statusGroup.setVisibility(arrowImg.isSelected() ? View.VISIBLE : View.GONE);
                }
            });
        }

        int status = UIModule.getStatus(alarmModule);

        statusImg.setImageLevel(status);

        statusName.setText(AppManager.getInstance().getModuleStatus(status));

        statusName.setTextColor(UICompat.getStatusColor(status));

        if (alarm)
        {
            // todo,call events feed!
            if (mEventsRequestProvider != null)
            {
                mEventsRequestProvider.request(HttpUrl.getEventsUrl());
            }

            View inflaterView = inflater.inflate(R.layout.comp_home_status_alarm, statusGroup, false);

            statusGroup.addView(inflaterView);

            mAlarmViewDataBinding = DataBindingAdapterUtil.viewBinding(inflaterView, BR.data, new UIModule(alarmModule));
        }
        else
        {
            for (Module module : modules)
            {
                View inflaterView = inflater.inflate(R.layout.comp_home_status_detail, statusGroup, false);

                statusGroup.addView(DataBindingAdapterUtil.binding(inflaterView, BR.data, new UIModule(module)));
            }
        }
    }

    private void addModules(List<Module> modules, int start, int count)
    {
        int layoutId = R.layout.comp_home_module_one;

        if (count == 3)
        {
            layoutId = R.layout.comp_home_module_three;
        }
        else if (count == 2)
        {
            layoutId = R.layout.comp_home_module_two;
        }

        View inflate = getActivity().getLayoutInflater().inflate(layoutId, mContentContainer, false);

        mContentContainer.addView(inflate);

        ViewDataBinding viewDataBinding = DataBindingUtil.bind(inflate);

        viewDataBinding.setVariable(BR.data, ConvertUtil.convertModule(modules, start, start + count));

        viewDataBinding.setVariable(BR.handler, mDataBindingHandler);

        viewDataBinding.executePendingBindings();
    }

    private RequestCallback<Events> mEventsRequestCallback = new RequestCallback<Events>()
    {
        @Override
        public void onResponse(Events events)
        {
            Event event = events.getEvents().get(0);

            for (Event e : events.getEvents())
            {
                if (e.getStatus() > event.getStatus())
                {
                    event = e;
                }
            }

            DataBindingAdapterUtil.binding(mAlarmViewDataBinding, BR.data, new UIEvent(event));
        }

        @Override
        public void onError(Throwable throwable)
        {
        }
    };
}
