package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;

import butterknife.BindView;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-01
 * Time: 10:45
 */
public class DemoSettingFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.demo_setting_radio_group)
    RadioGroup mRadioGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_demo_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initComponent();
    }

    private void initComponent()
    {
        mRadioGroup.check(getCheckId());

        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.demo_setting_status_alarm:

                        AppManager.getInstance().setDemoModeStatus(Constants.STATUS_WARNING);

                        break;

                    case R.id.demo_setting_status_error:

                        AppManager.getInstance().setDemoModeStatus(Constants.STATUS_ERROR);

                        break;

                    default:

                        AppManager.getInstance().setDemoModeStatus(Constants.STATUS_NORMAL);

                        break;
                }
            }
        });
    }

    private int getCheckId()
    {
        switch (AppManager.getInstance().getDemoModeStatus())
        {
            case Constants.STATUS_WARNING:

                return R.id.demo_setting_status_alarm;

            case Constants.STATUS_ERROR:

                return R.id.demo_setting_status_error;

            default:

                return R.id.demo_setting_status_normal;
        }
    }
}
