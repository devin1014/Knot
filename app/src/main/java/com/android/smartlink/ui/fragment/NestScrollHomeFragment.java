package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherProvider;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.DetailActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.modulestatus.ModuleStatusLayout;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.HttpUrl;
import com.neulion.core.widget.recyclerview.handler.DataBindingHandler;
import com.umeng.analytics.MobclickAgent;

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

    @BindView(R.id.module_status)
    ViewGroup mModuleStatus;

    private MainRequestProvider mRequestProvider;

    private WeatherProvider mWeatherProvider;

    private ViewDataBinding mWeatherBinding;

    private ModuleStatusLayout mModuleStatusLayout;

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
        mModuleStatusLayout = new ModuleStatusLayout(mModuleStatus);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRequestProvider = new MainRequestProvider(getActivity(), this);

        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mWeatherProvider = new WeatherProvider(getActivity(), mWeatherRequestCallback);

        //fixme,location = shanghai
        mWeatherProvider.request(HttpUrl.getWeatherUrl(getActivity(), "shanghai"));

        mLoadingLayout.showLoading();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MobclickAgent.onPageStart("Home");
    }

    @Override
    public void onPause()
    {
        MobclickAgent.onPageEnd("Home");

        super.onPause();
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        mWeatherProvider.destroy();

        mSwipeRefreshLayout.setRefreshing(false);

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        mLoadingLayout.showContent();

        mSwipeRefreshLayout.setRefreshing(false);

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
        mRequestProvider.request(HttpUrl.getHomeUrl());

        //fixme,location=shanghai
        mWeatherProvider.request(HttpUrl.getWeatherUrl(getActivity(), "shanghai"));
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
        mModuleStatusLayout.setModules(modules);

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

    private RequestCallback<Weather> mWeatherRequestCallback = new RequestCallback<Weather>()
    {
        @Override
        public void onResponse(Weather weather)
        {
            if (mWeatherBinding == null)
            {
                mWeatherBinding = DataBindingUtil.bind(mWeatherRoot);
            }

            mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

            mWeatherBinding.executePendingBindings();
        }

        @Override
        public void onError(Throwable throwable)
        {
        }
    };

}
