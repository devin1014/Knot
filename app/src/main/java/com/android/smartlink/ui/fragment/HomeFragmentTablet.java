package com.android.smartlink.ui.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AlertNotifyManager;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherProvider;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.MainActivityTablet;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UITime;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.util.HttpUrl;
import com.android.smartlink.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-05
 * Time: 18:05
 */
public class HomeFragmentTablet extends BaseSmartlinkFragment implements RequestCallback<Modules>, OnRefreshListener
{
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.weather_root)
    View mWeatherRoot;

    @BindView(R.id.home_location)
    TextView mLocation;

    @BindView(R.id.home_main_module)
    View mMainModuleView;

    @BindView(R.id.home_clock_panel)
    View mClockView;

    @BindView(R.id.home_module_container)
    LinearLayout mModuleContainer;

    private MainRequestProvider mRequestProvider;

    private WeatherProvider mWeatherProvider;

    private ViewDataBinding mWeatherBinding;

    private ViewDataBinding mMainModuleBinding;

    private ViewDataBinding mClockBinding;

    private ClockHandler mClockHandler;

    private boolean mClockMode = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mClockHandler = new ClockHandler();

        mWeatherBinding = DataBindingUtil.bind(mWeatherRoot);

        mMainModuleBinding = DataBindingUtil.bind(mMainModuleView);

        mClockBinding = DataBindingUtil.bind(mClockView);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mWeatherRoot.setKeepScreenOn(true);

        mWeatherProvider = new WeatherProvider(getActivity(), mWeatherRequestCallback);

        //fixme,location = shanghai
        mWeatherProvider.request(HttpUrl.getWeatherUrl(getActivity(), "shanghai"));

        mLocation.setText("上海");

        mRequestProvider = new MainRequestProvider(getActivity(), this);

        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mLoadingLayout.showLoading();
    }

    @Override
    public void onDestroyView()
    {
        mWeatherProvider.destroy();

        mRequestProvider.destroy();

        mClockHandler.removeMessage();

        mSwipeRefreshLayout.setRefreshing(false);

        AlertNotifyManager.removeAllNotification(getActivity());

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        mLoadingLayout.showContent();

        mSwipeRefreshLayout.setRefreshing(false);

        boolean alarm = false;

        List<UIModule> moduleList = new ArrayList<>();

        for (int i = 0; i < modules.getModules().size(); i++)
        {
            UIModule m = new UIModule(modules.getModules().get(i));

            moduleList.add(m);

            if (!alarm)
            {
                alarm = m.isAlarm() || m.isError();
            }
        }

        mMainModuleBinding.setVariable(BR.data, moduleList.get(0));

        mMainModuleBinding.executePendingBindings();

        if (mClockMode && !alarm)
        {
            resetClock();
        }
        else
        {
            resetModules(moduleList, 1, moduleList.size());

            if (!alarm)
            {
                mClockHandler.sendMessage(60 * 1000);

                AlertNotifyManager.removeAllNotification(getActivity());
            }
            else
            {
                mClockHandler.removeMessage();

                AlertNotifyManager.showNotification(getActivity(), moduleList);
            }
        }

        ((MainActivityTablet) getActivity()).hideNavigationBar();
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
        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);
    }

    @OnClick(R.id.home_main_container)
    public void onMainContainerClick()
    {
        if (mClockMode)
        {
            mModuleContainer.setVisibility(View.VISIBLE);

            mClockView.setVisibility(View.GONE);

            mClockMode = false;

            ScreenUtil.showModule(getActivity());
        }
    }

    private RequestCallback<Weather> mWeatherRequestCallback = new RequestCallback<Weather>()
    {
        @Override
        public void onResponse(Weather weather)
        {
            mSwipeRefreshLayout.setRefreshing(false);

            mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

            mWeatherBinding.executePendingBindings();
        }

        @Override
        public void onError(Throwable throwable)
        {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private void resetClock()
    {
        ScreenUtil.showClock(getActivity());

        mClockBinding.setVariable(BR.data, new UITime());

        mClockBinding.executePendingBindings();

        mClockView.setVisibility(View.VISIBLE);

        mModuleContainer.setVisibility(View.GONE);
    }

    @SuppressWarnings("SameParameterValue")
    private void resetModules(List<UIModule> modules, int start, int end)
    {
        ScreenUtil.showModule(getActivity());

        mModuleContainer.removeAllViews();

        for (int i = start; i < end; i++)
        {
            UIModule m = modules.get(i);

            View inflate = getActivity().getLayoutInflater().inflate(R.layout.comp_module_item, mModuleContainer, false);

            mModuleContainer.addView(inflate);

            ViewDataBinding viewDataBinding = DataBindingUtil.bind(inflate);

            viewDataBinding.setVariable(BR.data, m);

            viewDataBinding.executePendingBindings();
        }

        mModuleContainer.setVisibility(View.VISIBLE);

        mClockView.setVisibility(View.GONE);

        mClockMode = false;
    }

    @SuppressLint("HandlerLeak")
    private class ClockHandler extends Handler
    {
        private final int MSG_CLOCK = 1;

        private boolean mSendMessage = false;

        @Override
        public void handleMessage(Message msg)
        {
            resetClock();

            mClockMode = true;
        }

        void sendMessage(long delay)
        {
            if (!mSendMessage)
            {
                mSendMessage = true;

                sendEmptyMessageDelayed(MSG_CLOCK, delay);
            }
        }

        void removeMessage()
        {
            mSendMessage = false;

            removeMessages(MSG_CLOCK);
        }
    }

}
