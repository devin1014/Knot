package com.android.smartlink.ui.widget.modulestatus;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.smartlink.Constants;
import com.android.smartlink.R;
import com.android.smartlink.bean.Modules.Module;
import com.android.smartlink.ui.model.UIModule;

import java.util.List;

/**
 * User: NeuLion(wei.liu@neulion.com.com)
 * Date: 2017-11-16
 * Time: 14:47
 */
public class ModuleStatusLayout
{
    private LayoutInflater mLayoutInflater;

    private ViewGroup mViewGroup;

    private NormalModuleStatusView mNormalModuleStatusView;

    private AlarmModuleStatusView mAlarmModuleStatusView;

    public ModuleStatusLayout(ViewGroup rootView)
    {
        mViewGroup = rootView;

        mLayoutInflater = LayoutInflater.from(rootView.getContext());
    }

    private List<Module> mModules;

    public final void setModules(List<Module> modules)
    {
        final List<Module> mOldList = mModules;

        if (mModules != modules)
        {
            mModules = modules;

            if (getMaxStatus(modules) == Constants.STATUS_NORMAL)
            {
                if (mNormalModuleStatusView == null)
                {
                    mNormalModuleStatusView = (NormalModuleStatusView) mLayoutInflater.inflate(R.layout.comp_module_status_normal, mViewGroup, false);
                }

                mNormalModuleStatusView.onModuleChanged(modules, mOldList);

                if (mNormalModuleStatusView.getParent() == null)
                {
                    mViewGroup.addView(mNormalModuleStatusView);
                }

                if (mAlarmModuleStatusView != null)
                {
                    mViewGroup.removeView(mAlarmModuleStatusView);
                }
            }
            else
            {
                if (mAlarmModuleStatusView == null)
                {
                    mAlarmModuleStatusView = (AlarmModuleStatusView) mLayoutInflater.inflate(R.layout.comp_module_status_alarm, mViewGroup, false);
                }

                mAlarmModuleStatusView.onModuleChanged(modules, mOldList);

                if (mAlarmModuleStatusView.getParent() == null)
                {
                    mViewGroup.addView(mAlarmModuleStatusView);
                }

                if (mNormalModuleStatusView != null)
                {
                    mViewGroup.removeView(mNormalModuleStatusView);
                }
            }
        }
    }

    private int getMaxStatus(List<Module> list)
    {
        Module alarmModule = list.get(0);

        for (Module module : list)
        {
            if (module.getStatus() == Constants.STATUS_ERROR)
            {
                alarmModule = module;

                break;
            }

            if (UIModule.getStatus(module) > UIModule.getStatus(alarmModule))
            {
                alarmModule = module;
            }
        }

        return UIModule.getStatus(alarmModule);
    }
}
