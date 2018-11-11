package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.smartlink.BR;
import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AlertNotifyManager;
import com.android.smartlink.assist.MainRequestProvider;
import com.android.smartlink.assist.RequestCallback;
import com.android.smartlink.assist.WeatherManager;
import com.android.smartlink.assist.WeatherManager.WeatherCallback;
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.util.UIConverter;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-12-05
 * Time: 18:05
 */
public class HomeFragmentTablet extends BaseSmartlinkFragment implements RequestCallback<ModulesData>
{
    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;
    @BindView(R.id.weather_root)
    View mWeatherView;

    private MainRequestProvider mRequestProvider;

    private WeatherManager mWeatherManager;

    private AlertNotifyManager mAlertManager;

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
        mAlertManager = new AlertNotifyManager(getActivity());

        final ViewDataBinding mWeatherBinding = DataBindingUtil.bind(mWeatherView);

        mWeatherManager = new WeatherManager(new WeatherCallback()
        {
            @Override
            public void onWeatherResponse(Weather weather)
            {
                if (mWeatherBinding != null)
                {
                    mWeatherBinding.setVariable(BR.data, new UIWeather(weather));

                    mWeatherBinding.executePendingBindings();
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

    @Override
    public void onResponse(ModulesData modules)
    {
        mLoadingLayout.showContent();

        mAlertManager.notifyNotification(getActivity(), UIConverter.convertModules(modules.getMonitorModules()));

        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.home_module_container);

        if (fragment == null)
        {
            replaceFragment(R.id.home_module_container, ModuleFragment.newInstance(modules));
        }
        else if (fragment instanceof ModuleFragment)
        {
            ((ModuleFragment) fragment).notifyModulesChanged(modules);
        }

        Fragment toggleFragment = getChildFragmentManager().findFragmentById(R.id.home_toggle_container);

        if (toggleFragment == null)
        {
            replaceFragment(R.id.home_toggle_container, ToggleFragment.newInstance(modules));
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
    }

}
