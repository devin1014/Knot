package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.smartlink.R;
import com.android.smartlink.assist.EnergyRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.eventbus.EventBusMessages.EventModuleDataChanged;
import com.android.smartlink.bean.Energy;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.ModulesData.MonitorModuleData;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.android.smartlink.ui.widget.Last15DaysPowerChart;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.util.Utils;

import org.achartengine.GraphicalView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * User: liuwei
 * Date: 2018-05-20
 * Time: 14:03
 */
public class ModuleChartFragment extends BaseSmartlinkFragment implements RequestCallback<Energy>
{
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.chart_right_panel)
    FrameLayout mChartLayout2;
    @BindView(R.id.chart_electricity)
    TextView mCurrent;
    @BindView(R.id.chart_voltage)
    TextView mVoltage;
    @BindView(R.id.chart_power)
    TextView mPower;
    @BindView(R.id.chart_power_factor)
    TextView mPowerFactor;
    @BindView(R.id.chart_energy)
    TextView mEnergy;

    private EnergyRequestProvider mRequestProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_module_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mRequestProvider = EnergyRequestProvider.newInstance(this);

        mRequestProvider.request(RequestUrl.obtainEnergyUrl());

        //mLoadingLayout.showLoading();
        mLoadingLayout.showContent();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        super.onDestroyView();
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModuleDataChangedEvent(EventModuleDataChanged event)
    {
        if (mUIMonitorModule != null)
        {
            ModulesData modulesData = event.modulesData;

            for (MonitorModuleData d : modulesData.getMonitorModules())
            {
                if (d.getSlaveID() == mUIMonitorModule.getSlaveID()
                        && d.getChannel() == mUIMonitorModule.getChannel())
                {
                    setModuleData(new MonitorModuleImp(d));

                    break;
                }
            }
        }
    }

    private UIMonitorModule mUIMonitorModule;

    public void setModuleData(UIMonitorModule moduleData)
    {
        mUIMonitorModule = moduleData;
        mCurrent.setText(Utils.formatString(getString(R.string.detail_current), moduleData.getCurrent()));
        mVoltage.setText(Utils.formatString(getString(R.string.detail_voltage), moduleData.getVoltage()));
        mPower.setText(Utils.formatString(getString(R.string.detail_power), moduleData.getPower()));
        mPowerFactor.setText(Utils.formatString(getString(R.string.detail_power_factor), moduleData.getPowerFactor()));
        mEnergy.setText(Utils.formatString(getString(R.string.detail_energy), moduleData.getEnergy()));
    }

    @Override
    public void onResponse(Energy energy)
    {
        mLoadingLayout.showContent();

        mChartLayout2.removeAllViews();

        mChartLayout2.addView(new GraphicalView(getActivity(), new Last15DaysPowerChart().getChart(getActivity(), energy.getData())));
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));
    }
}
