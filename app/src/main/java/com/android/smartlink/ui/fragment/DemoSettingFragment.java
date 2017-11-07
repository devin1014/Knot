package com.android.smartlink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.application.manager.AppManager;
import com.android.smartlink.ui.fragment.base.BaseSmartlinkFragment;
import com.android.smartlink.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-01
 * Time: 10:45
 */
public class DemoSettingFragment extends BaseSmartlinkFragment
{
    @BindView(R.id.setting_module_151)
    RadioGroup mMainModule;

    @BindView(R.id.setting_module_150)
    RadioGroup mFreezerModule;

    @BindView(R.id.setting_module_152)
    RadioGroup mOvenModule;

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
        int status = AppManager.getInstance().getDemoModeStatus();

        int mainStatus = status / 100;

        setModule(mMainModule, mainStatus);

        int freezerStatus = (status / 10) % 10;

        setModule(mFreezerModule, freezerStatus);

        int ovenStatus = status % 10;

        setModule(mOvenModule, ovenStatus);
    }

    private void setModule(RadioGroup group, int status)
    {
        if (status == Constants.STATUS_NORMAL)
        {
            ((RadioButton) group.getChildAt(0)).setChecked(true);
        }
        else if (status == Constants.STATUS_WARNING)
        {
            ((RadioButton) group.getChildAt(1)).setChecked(true);
        }
        else if (status == Constants.STATUS_ERROR)
        {
            ((RadioButton) group.getChildAt(2)).setChecked(true);
        }
    }

    @OnClick(R.id.settings_ok)
    public void setDemoStatus()
    {
        int mainStatus = getFlag(mMainModule) * 100;

        int freezerStatus = getFlag(mFreezerModule) * 10;

        int ovenStatus = getFlag(mOvenModule);

        int status = mainStatus + freezerStatus + ovenStatus;

        AppManager.getInstance().setDemoModeStatus(status);

        LogUtil.log(this, "status:" + status);

        Toast.makeText(getActivity(), getString(R.string.demo_setting_success), Toast.LENGTH_SHORT).show();

        getActivity().finish();
    }

    private int getFlag(RadioGroup radioGroup)
    {
        int index = radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()));

        if (index == 1)
        {
            return Constants.STATUS_WARNING;
        }
        else if (index == 2)
        {
            return Constants.STATUS_ERROR;
        }
        else
        {
            return Constants.STATUS_NORMAL;
        }
    }
}
