package com.android.smartlink.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.android.smartlink.bean.ModulesData;
import com.android.smartlink.bean.RequestUrl;
import com.android.smartlink.bean.Weather;
import com.android.smartlink.ui.activity.DetailActivity;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.ui.model.MonitorModuleImp;
import com.android.smartlink.ui.model.UIMonitorModule;
import com.android.smartlink.ui.model.UIWeather;
import com.android.smartlink.ui.widget.LoadingLayout;
import com.android.smartlink.ui.widget.adapter.ModuleAdapter;
import com.android.smartlink.ui.widget.layoutmanager.ModuleGridLayoutManager;
import com.android.smartlink.util.ConvertUtil;
import com.neulion.core.widget.recyclerview.RecyclerView;
import com.neulion.core.widget.recyclerview.listener.OnItemClickListener;

import butterknife.BindView;

/**
 * User: LIUWEI
 * Date: 2017-10-27
 * Time: 10:18
 */
public class HomeFragment extends BaseSmartlinkFragment implements RequestCallback<ModulesData>
{
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
        mRecyclerView.setLayoutManager(new ModuleGridLayoutManager(getActivity(), 4));

        mRecyclerView.setAdapter(mModuleAdapter = new ModuleAdapter(getLayoutInflater(), mOnItemClickListener));

        mLoadingLayout.showLoading();

        mRequestProvider = MainRequestProvider.newInstance(this);

        mRequestProvider.schedule(RequestUrl.obtainMainDataUrl(), 0, Constants.REQUEST_SCHEDULE_INTERVAL);

        mWeatherManager = new WeatherManager(mWeatherCallback);

        mWeatherManager.requestWeather();
    }

    @Override
    public void onDestroyView()
    {
        mRequestProvider.destroy();

        mWeatherManager.destroy();

        super.onDestroyView();
    }

    @Override
    public void onResponse(ModulesData modules)
    {
        mLoadingLayout.showContent();

        //TODO
        //        if (mModuleAdapter.getHeadCount() == 0)
        //        {
        //            mModuleAdapter.addHeadObject(modules.getMonitorModules());
        //        }
        //        else
        //        {
        //            ViewHolder holder = mRecyclerView.findViewHolderForLayoutPosition(0);
        //
        //            if (holder instanceof HeadHolder)
        //            {
        //                ((HeadHolder) holder).getModuleStatusLayout().setModules(modules.getMonitorModules());
        //            }
        //        }

        mModuleAdapter.setData(ConvertUtil.convertModule(modules));
    }

    @Override
    public void onError(Throwable throwable)
    {
        mLoadingLayout.showMessage(getString(R.string.request_data_error));
    }

    private OnItemClickListener<UIMonitorModule> mOnItemClickListener = new OnItemClickListener<UIMonitorModule>()
    {
        private BaseExecutorService mExecutorService = new BaseExecutorService();

        @Override
        public void onItemClick(View view, UIMonitorModule iModule)
        {
            if (iModule.isToggle())
            {
                boolean isToggleOn = view.isSelected();

                int value = isToggleOn ? MODULE_FLAG.CTRL_OFF.value : MODULE_FLAG.CTRL_ON.value;

                mExecutorService.execute(iModule.getId(), 255, value);//FIXME,deviceId

                view.setSelected(!isToggleOn);
            }
            else
            {
                DetailActivity.startActivity(getActivity(), iModule.getName(), (MonitorModuleImp) iModule);
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
