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

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherManager;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.DetailActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.ModuleAdapter;
import com.android.smartlink.ui.widget.layoutmanager.MyGridLayoutManager;
import com.android.smartlink.ui.widget.modulestatus.ModuleStatusLayout;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.HttpUrl;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter;
import com.neulion.core.widget.recyclerview.adapter.DataBindingAdapter.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-27
 * Time: 10:18
 */
public class HomeFragment extends BaseSmartlinkFragment implements RequestCallback<Modules>, OnRefreshListener
{
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.weather_root)
    View mWeatherRoot;

    @BindView(R.id.module_status)
    ViewGroup mModuleStatus;

    private MainRequestProvider mRequestProvider;

    private ViewDataBinding mWeatherBinding;

    private ModuleStatusLayout mModuleStatusLayout;

    private ModuleAdapter mModuleAdapter;

    private WeatherManager mWeatherManager;

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
        mModuleStatusLayout = new ModuleStatusLayout(mModuleStatus);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new MyGridLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mModuleAdapter = new ModuleAdapter(getLayoutInflater(), mOnItemClickListener));

        mLoadingLayout.showLoading();

        mRequestProvider = new MainRequestProvider(getActivity(), this);

        mRequestProvider.schedule(HttpUrl.getHomeUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mWeatherManager = new WeatherManager(getActivity(), mWeatherCallback);

        mWeatherManager.requestWeather();
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

        mWeatherManager.destroy();

        mSwipeRefreshLayout.setRefreshing(false);

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        mLoadingLayout.showContent();

        mSwipeRefreshLayout.setRefreshing(false);

        mModuleStatusLayout.setModules(modules.getModules());

        mModuleAdapter.setData(ConvertUtil.convertModule(modules.getModules()));
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

        mWeatherManager.requestWeather();
    }

    private OnItemClickListener<UIModule> mOnItemClickListener = new OnItemClickListener<UIModule>()
    {
        @Override
        public void onItemClick(DataBindingAdapter<UIModule> dataBindingAdapter, View view, UIModule uiModule, int i)
        {
            DetailActivity.startActivity(getActivity(), uiModule.getName(), uiModule);
        }
    };

    private WeatherManager.WeatherCallback mWeatherCallback = new WeatherManager.WeatherCallback()
    {
        @Override
        public void onWeatherResponse(Weather weather)
        {
            if (mWeatherBinding == null)
            {
                mWeatherBinding = DataBindingUtil.bind(mWeatherRoot);
            }

            mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

            mWeatherBinding.executePendingBindings();
        }
    };

}
