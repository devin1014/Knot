package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AlertNotifyManager;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherManager;
import com.android.smartlink.assist.WeatherManager.WeatherCallback;
import com.android.smartlink.assist.eventbus.EventBusMessages.EventModuleDataChanged;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.databinding.LayoutHomeWeatherBindingImpl;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.util.ConvertUtil;
import com.android.smartlink.util.FragmentUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-12-05
 * Time: 18:05
 */
public class HomeFragment extends BaseSmartlinkFragment implements RequestCallback<ModulesData>
{
    @BindView(R.id.home_loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.weather_root)
    View mWeatherView;

    private MainRequestProvider mRequestProvider;

    private WeatherManager mWeatherManager;

    private AlertNotifyManager mAlertManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
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
        mAlertManager = new AlertNotifyManager(getActivity());

        final LayoutHomeWeatherBindingImpl viewBinding = DataBindingUtil.bind(mWeatherView);

        mWeatherManager = new WeatherManager(new WeatherCallback()
        {
            @Override
            public void onWeatherResponse(Weather weather)
            {
                if (viewBinding != null)
                {
                    viewBinding.setData(new UIWeather(weather));

                    viewBinding.executePendingBindings();
                }
            }
        });

        mWeatherManager.requestWeather();

        mRequestProvider = MainRequestProvider.newInstance(this);

        mRequestProvider.schedule(RequestUrl.obtainMainDataUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mLoadingLayout.showLoading();
    }

    @Override
    public void onStop()
    {
        super.onStop();

        mAlertManager.stopWarning();
    }

    @Override
    public void onDestroyView()
    {
        mWeatherManager.destroy();

        mRequestProvider.destroy();

        super.onDestroyView();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResponse(ModulesData modules)
    {
        mLoadingLayout.showContent();

        mAlertManager.notifyNotification(getActivity(), ConvertUtil.convertModules(modules.getMonitorModules()));

        int leftHolderId = R.id.home_module_holder;

        if (getChildFragmentManager().findFragmentById(leftHolderId) == null)
        {
            replaceFragment(leftHolderId,
                    FragmentUtils.newInstance(ModuleFragment.class, new Pair<String, Serializable>(Constants.KEY_EXTRA_MODULES, modules)));
        }

        int rightHolderId = R.id.home_toggle_holder;

        if (getChildFragmentManager().findFragmentById(rightHolderId) == null)
        {
            replaceFragment(rightHolderId,
                    FragmentUtils.newInstance(ToggleListFragment.class, new Pair<String, Serializable>(Constants.KEY_EXTRA_MODULES, modules)));
        }

        EventBus.getDefault().post(new EventModuleDataChanged(modules));
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));
    }

}
