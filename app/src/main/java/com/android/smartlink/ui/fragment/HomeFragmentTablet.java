package com.android.smartlink.ui.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherProvider;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UITime;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.util.HttpUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-05
 * Time: 18:05
 */
public class HomeFragmentTablet extends BaseSmartlinkFragment implements RequestCallback<Modules>
{
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

        mWeatherRoot.setKeepScreenOn(true);

        mWeatherProvider = new WeatherProvider(getActivity(), mWeatherRequestCallback);

        //fixme,location = shanghai
        mWeatherProvider.request(HttpUrl.getWeatherUrl(getActivity(), "shanghai"));

        mLocation.setText("上海");

        mRequestProvider = new MainRequestProvider(getActivity(), this);

        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);
    }

    @Override
    public void onDestroyView()
    {
        mWeatherProvider.destroy();

        mRequestProvider.destroy();

        mClockHandler.removeMessage();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        UIModule mainModule = new UIModule(modules.getModules().get(0));

        mMainModuleBinding.setVariable(BR.data, mainModule);

        mMainModuleBinding.executePendingBindings();

        boolean alarm = mainModule.isAlarm();

        List<UIModule> list = new ArrayList<>();

        for (int i = 1; i < modules.getModules().size(); i++)
        {
            UIModule m = new UIModule(modules.getModules().get(i));

            list.add(m);

            if (!alarm)
            {
                alarm = m.isAlarm() || m.isError();
            }
        }

        if (mClockMode && !alarm)
        {
            resetClock();
        }
        else
        {
            resetModules(list);

            if (!alarm)
            {
                mClockHandler.sendMessage(60 * 1000);
            }
            else
            {
                mClockHandler.removeMessage();
            }
        }
    }

    @Override
    public void onError(Throwable throwable)
    {
    }

    private RequestCallback<Weather> mWeatherRequestCallback = new RequestCallback<Weather>()
    {
        @Override
        public void onResponse(Weather weather)
        {
            mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

            mWeatherBinding.executePendingBindings();
        }

        @Override
        public void onError(Throwable throwable)
        {
        }
    };

    private void resetClock()
    {
        mClockBinding.setVariable(BR.data, new UITime());

        mClockBinding.executePendingBindings();

        mClockView.setVisibility(View.VISIBLE);

        mModuleContainer.setVisibility(View.GONE);
    }

    private void resetModules(List<UIModule> modules)
    {
        mModuleContainer.removeAllViews();

        for (UIModule m : modules)
        {
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
