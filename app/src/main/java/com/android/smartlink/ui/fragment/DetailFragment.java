package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.assist.EnergyRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.ScheduleHandler;
import com.android.smartlink.bean.Energy;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.widget.IndicatorView;
import com.android.smartlink.ui.widget.Last30DaysPowerChart;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.SuggestPagerAdapter;
import com.android.smartlink.util.AppDataBindingAdapter;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import org.achartengine.GraphicalView;

import java.text.DecimalFormat;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-23
 * Time: 11:27
 */
public class DetailFragment extends BaseSmartlinkFragment implements RequestCallback<Energy>, ScheduleHandler.OnScheduleListener
{
    @SuppressWarnings("unused")
    public static DetailFragment newInstance(MonitorModuleImp module)
    {
        DetailFragment fragment = new DetailFragment();

        Bundle arguments = new Bundle();

        arguments.putSerializable(Constants.KEY_EXTRA_UI_MODULE, module);

        fragment.setArguments(arguments);

        return fragment;
    }

    public static DetailFragment newInstance(Bundle arguments)
    {
        DetailFragment fragment = new DetailFragment();

        fragment.setArguments(arguments);

        return fragment;
    }

    @BindView(R.id.detail_suggest)
    ViewPager mViewPager;

    @BindView(R.id.detail_indicator)
    IndicatorView mIndicatorView;

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @BindView(R.id.chart_container)
    FrameLayout mChartContainer;

    @BindView(R.id.detail_power_consume_total)
    TextView mTotalPower;

    private ScheduleHandler mScheduleHandler;

    private EnergyRequestProvider mRequestProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent(view);
    }

    private void initComponent(View view)
    {
        MonitorModuleImp uiModule = (MonitorModuleImp) getArguments().getSerializable(Constants.KEY_EXTRA_UI_MODULE);

        AppDataBindingAdapter.binding(view, new int[]{BR.data, BR.itemClickListener}, new Object[]{uiModule, mOnItemClickListener});

        mViewPager.setAdapter(new SuggestPagerAdapter(getActivity()));

        mIndicatorView.setViewPager(mViewPager);

        mRequestProvider = EnergyRequestProvider.newInstance(this);

        assert uiModule != null;
        mRequestProvider.request(RequestUrl.obtainEnergyUrl(uiModule.getId()));

        mLoadingLayout.showLoading();

        mScheduleHandler = new ScheduleHandler();

        mScheduleHandler.setOnScheduleListener(this);

        mScheduleHandler.schedule(30 * 1000);
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        mScheduleHandler.cancel();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Energy energy)
    {
        mLoadingLayout.showContent();

        mChartContainer.removeAllViews();

        mChartContainer.addView(new GraphicalView(getActivity(), new Last30DaysPowerChart().getChart(getActivity(), energy.getData())));

        String powerFormat = AppManager.getInstance().getString(R.string.format_power);

        mTotalPower.setText(String.format(powerFormat, new DecimalFormat("0.0").format(energy.getTotal())));
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));
    }

    @Override
    public void onScheduled()
    {
        int nextPos = mViewPager.getCurrentItem() + 1;

        if (nextPos >= mViewPager.getAdapter().getCount())
        {
            nextPos = 0;
        }

        mViewPager.setCurrentItem(nextPos, true);
    }

    private OnItemClickListener<MonitorModuleImp> mOnItemClickListener = new OnItemClickListener<MonitorModuleImp>()
    {
        @Override
        public void onItemClick(View view, MonitorModuleImp module)
        {
            showDetailFragment(EventsFragment.newInstance(module.getId()), getString(R.string.events));
        }
    };

}
