package com.android.smartlink.ui.fragment;

import android.content.Intent;
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
import com.android.devin.core.ui.widget.recyclerview.DataBindingHandler;
import com.android.smartlink.R;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherRequestProvider;
import com.android.smartlink.bean.Modules;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.DetailActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIModule;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.HomeAdapter;
import com.android.smartlink.util.ConvertUtil;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-10-16
 * Time: 17:55
 */
public class HomeFragment extends BaseSmartlinkFragment implements RequestCallback<Modules>, OnRefreshListener
{
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MainRequestProvider mRequestProvider;

    private WeatherRequestProvider mWeatherRequestProvider;

    private HomeAdapter mHomeAdapter;

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

        initComponent(view);
    }

    private void initComponent(View view)
    {
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new CommonItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.home_list_divider)));

        mRecyclerView.setAdapter(mHomeAdapter = new HomeAdapter(getActivity().getLayoutInflater(), mOnItemClickListener));

        mRequestProvider = new MainRequestProvider(this);

        mRequestProvider.request("http://localhost:8080/examples/smartlink/main.json");

        mLoadingLayout.showLoading();

        mWeatherRequestProvider = new WeatherRequestProvider(mWeatherRequestCallback);

        //mWeatherRequestProvider.request("http://www.sojson.com/open/api/weather/json.shtml?city=上海");
        mWeatherRequestProvider.request("http://localhost:8080/examples/smartlink/weather.json");
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        mWeatherRequestProvider.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Modules modules)
    {
        mLoadingLayout.showContent();

        mSwipeRefreshLayout.setRefreshing(false);

        mHomeAdapter.setData(ConvertUtil.convertModule(modules.getModules()));
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
        mRequestProvider.request("http://localhost:8080/examples/smartlink/main.json");

        // hide loading and show blank loading view
        mLoadingLayout.showBlankView();
    }

    private RequestCallback<Weather> mWeatherRequestCallback = new RequestCallback<Weather>()
    {
        @Override
        public void onResponse(Weather weather)
        {
        }

        @Override
        public void onError(Throwable throwable)
        {
        }
    };

    private DataBindingHandler<UIModule> mOnItemClickListener = new DataBindingHandler<UIModule>()
    {
        @Override
        public void onItemClick(View view, UIModule uiModule)
        {
            //// TODO: 2017/10/19
            startActivity(new Intent(getActivity(), DetailActivity.class));
        }
    };
}
