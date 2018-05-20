package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherManager;
import com.android.smartlink.assist.WeatherManager.WeatherCallback;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.util.HttpUrl;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-12-05
 * Time: 18:05
 */
public class HomeFragmentTablet extends BaseSmartlinkFragment implements RequestCallback<Modules>, OnRefreshListener
{
    //    @BindView(R.id.swipe_refresh_layout)
    //    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.weather_root)
    View mWeatherView;
    //    @BindView(R.id.home_main_module)
    //    View mMainModuleView;
    //    @BindView(R.id.model_list)
    //    RecyclerView mModelRecyclerView;
    //    @BindView(R.id.toggle_list)
    //    RecyclerView mToggleRecyclerView;

    private MainRequestProvider mRequestProvider;

    private WeatherManager mWeatherManager;

    private ViewDataBinding mWeatherBinding;

    //    private ViewDataBinding mMainModuleBinding;
    //
    //    private ModuleAdapterTablet mModuleAdapter;
    //
    //    private ToggleAdapterTablet mToggleAdapter;

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
        mWeatherBinding = DataBindingUtil.bind(mWeatherView);

        //        mMainModuleBinding = DataBindingUtil.bind(mMainModuleView);

        //        mSwipeRefreshLayout.setOnRefreshListener(this);

        //mToggleRecyclerView.setAdapter(mToggleAdapter = new ToggleAdapterTablet(getLayoutInflater(), null));//todo

        mWeatherManager = new WeatherManager(getActivity(), mWeatherCallback);

        mWeatherManager.requestWeather();

        mRequestProvider = new MainRequestProvider(getActivity(), this);

        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mLoadingLayout.showLoading();
    }

    @Override
    public void onDestroyView()
    {
        mWeatherManager.destroy();

        mRequestProvider.destroy();

        //        mSwipeRefreshLayout.setRefreshing(false);

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        mLoadingLayout.showContent();

        //        mSwipeRefreshLayout.setRefreshing(false);

        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.module_panel);

        if (fragment == null)
        {
            getChildFragmentManager().beginTransaction().replace(R.id.module_panel, ModuleFragment.newInstance(modules)).commit();
        }
        else if (fragment instanceof ModuleFragment)
        {
            ((ModuleFragment) fragment).notifyModulesChanged(modules);
        }

        Fragment toggleFragment = getChildFragmentManager().findFragmentById(R.id.toggle_panel);

        if (toggleFragment == null)
        {
            getChildFragmentManager().beginTransaction().replace(R.id.toggle_panel, ToggleFragment.newInstance(modules)).commit();
        }
        else if (toggleFragment instanceof ToggleFragment)
        {
            ((ToggleFragment) toggleFragment).notifyModulesChanged(modules);
        }
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));

        //        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh()
    {
        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mWeatherManager.requestWeather();
    }

    private WeatherCallback mWeatherCallback = new WeatherCallback()
    {
        @Override
        public void onWeatherResponse(Weather weather)
        {
            //            mSwipeRefreshLayout.setRefreshing(false);

            mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

            mWeatherBinding.executePendingBindings();
        }
    };
}
