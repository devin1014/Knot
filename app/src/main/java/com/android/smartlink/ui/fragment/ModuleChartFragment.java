package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.smartlink.R;
import com.android.smartlink.assist.EnergyRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.bean.Energy;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.ui.fragment.base.BaseModulesFragment;
import com.android.smartlink.ui.widget.Last15DaysPowerChart;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.ModuleBarChart;

import org.achartengine.GraphicalView;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 14:03
 */
public class ModuleChartFragment extends BaseModulesFragment implements RequestCallback<Energy>
{
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.chart_left_panel)
    FrameLayout mChartLayout1;
    @BindView(R.id.chart_right_panel)
    FrameLayout mChartLayout2;

    private EnergyRequestProvider mRequestProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_module_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mRequestProvider = EnergyRequestProvider.newInstance(this);

        mRequestProvider.request(RequestUrl.obtainEnergyUrl(150));

        //mLoadingLayout.showLoading();
    }

    @Override
    public void notifyModulesChanged(ModulesData modules)
    {
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(Energy energy)
    {
        mLoadingLayout.showContent();

        ModuleBarChart barChart = new ModuleBarChart(getContext());

        barChart.setData(energy.getData());

        mChartLayout1.removeAllViews();

        mChartLayout1.addView(barChart);

        mChartLayout2.removeAllViews();

        mChartLayout2.addView(new GraphicalView(getActivity(), new Last15DaysPowerChart().getChart(getActivity(), energy.getData())));
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));
    }
}
