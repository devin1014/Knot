package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.Constants.MODULE_FLAG;
import com.android.smartlink.R;
import com.android.smartlink.assist.BaseExecutorService;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherManager;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.DetailActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.IModule;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.ModuleAdapter;
import com.android.smartlink.ui.widget.adapter.ModuleAdapter.HeadHolder;
import com.android.smartlink.ui.widget.layoutmanager.MyGridLayoutManager;
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
    View mWeatherView;

    private MainRequestProvider mRequestProvider;

    private ViewDataBinding mWeatherBinding;

    private ModuleAdapter mModuleAdapter;

    private WeatherManager mWeatherManager;

    private BaseExecutorService mExecutorService = new BaseExecutorService();

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

        if (mModuleAdapter.getHeadCount() == 0)
        {
            mModuleAdapter.addHeadObject(modules.getModules());
        }
        else
        {
            ViewHolder holder = mRecyclerView.findViewHolderForLayoutPosition(0);

            if (holder instanceof HeadHolder)
            {
                ((HeadHolder) holder).getModuleStatusLayout().setModules(modules.getModules());
            }
        }

        mModuleAdapter.setData(ConvertUtil.convertModule(modules));
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

    private OnItemClickListener<IModule> mOnItemClickListener = new OnItemClickListener<IModule>()
    {
        @Override
        public void onItemClick(DataBindingAdapter<IModule> dataBindingAdapter, View view, IModule module, int i)
        {
            if (module.isToggle())
            {
                boolean isToggleOn = view.isSelected();

                int value = isToggleOn ? MODULE_FLAG.CTRL_OFF.value : MODULE_FLAG.CTRL_ON.value;

                mExecutorService.execute(module.getId(), value);

                view.setSelected(!isToggleOn);
            }
            else
            {
                DetailActivity.startActivity(getActivity(), module.getName(), (UIModule) module);
            }
        }
    };

    private WeatherManager.WeatherCallback mWeatherCallback = new WeatherManager.WeatherCallback()
    {
        @Override
        public void onWeatherResponse(Weather weather)
        {
            if (mWeatherBinding == null)
            {
                mWeatherBinding = DataBindingUtil.bind(mWeatherView);
            }

            mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

            mWeatherBinding.executePendingBindings();
        }
    };

}
